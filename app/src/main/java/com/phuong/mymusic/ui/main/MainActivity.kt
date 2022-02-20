package com.phuong.mymusic.ui.main

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.phuong.mymusic.MusicPlayerRemote
import com.phuong.mymusic.MusicService
import com.phuong.mymusic.R
import com.phuong.mymusic.activities.SlidingMusicPanelActivity
import com.phuong.mymusic.model.Song
import com.phuong.mymusic.ui.detailsong.SendData
import com.phuong.mymusic.utils.CheckingPermission
import com.phuong.mymusic.utils.MusicUtils
import com.phuong.mymusic.utils.loadImage

class MainActivity : SlidingMusicPanelActivity() {
    private val TAG = "MainActivity"
    private lateinit var controller: NavController
    private var isStartSettingPermission = false
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var conn: ServiceConnection
    private var songService: MusicService? = null


    private fun initData() {
        mBinding.mainViewModel = mainViewModel
        controller = findNavController(R.id.nav_host_fragment)
        mBinding.bottomNav.setupWithNavController(controller)
        MusicPlayerRemote.createService(this)
        MusicPlayerRemote.createConnectionToService(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        if (CheckingPermission.showPermission(
                this, CheckingPermission.REQ_STORAGE_PERMISSION_CODE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            initData()
        }
    }

    override fun onDestroy() {
//        unbindService(conn)
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CheckingPermission.REQ_STORAGE_PERMISSION_CODE) {
            for (permission in permissions) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            permission
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        if (permission == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                            this.initData()
                            return
                        }
                    }
                    if (permission == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                        Log.d(TAG, "onRequestPermissionsResult: vào đây")
                        showDialogPermission()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isStartSettingPermission) {
            if (CheckingPermission.isNotStoragePmsGranted(this)) {
                finish()
                return
            }
            this.initData()
        }
    }

    private fun showDialogPermission() {
        AlertDialog.Builder(this)
            .setMessage(R.string.description_read_external_storage)
            .setTitle(R.string.grant_permission)
            .setPositiveButton(R.string.go_setting) { _: DialogInterface, type: Int ->
                CheckingPermission.goSettingsForPermission(this)
                isStartSettingPermission = true
            }
            .setNegativeButton(R.string.cancel_permission) { _: DialogInterface, type: Int ->
                finish()
            }
            .setCancelable(false)
            .show()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", this.packageName, null)
        intent.data = uri
        startActivityForResult(intent, 20)
    }
}