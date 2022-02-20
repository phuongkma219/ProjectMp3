package com.phuong.mymusic.activities

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.phuong.mymusic.MusicService
import com.phuong.mymusic.R
import com.phuong.mymusic.databinding.ActivityMainBinding
import com.phuong.mymusic.helper.musicViewModel
import com.phuong.mymusic.ui.MiniPlayerFragment
import com.phuong.mymusic.ui.detailsong.PlayerFragment
import com.phuong.mymusic.ui.mymusic.songs.SongsFragment
import com.phuong.newspaper.base.BaseActivity

abstract class SlidingMusicPanelActivity : BaseActivity<ActivityMainBinding>() {
    lateinit var miniPlayerFragment: MiniPlayerFragment
    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    private lateinit var conn: ServiceConnection
    private var musicService: MusicService? = null
    private var isServiceConnection = false
    val musicViewModel by viewModels<musicViewModel>()
    private val TAG = "SlidingMusicPanelActivi"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MyMusic)
        mBinding.lifecycleOwner = this
        val parentThatHasBottomSheetBehavior = mBinding?.slidingPanel as FrameLayout? ?: return
        bottomSheetBehavior = BottomSheetBehavior.from(parentThatHasBottomSheetBehavior)
        miniPlayerFragment = MiniPlayerFragment()
        miniPlayerFragment =
            supportFragmentManager.findFragmentById(R.id.player_song_mini_main) as MiniPlayerFragment
        miniPlayerFragment.view?.setOnClickListener {
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
            miniPlayerFragment.requireView().visibility = View.GONE
        }

        //
        val playerFragment = PlayerFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.player_song, playerFragment, playerFragment.tag).commit()
//        val songsFragment = SongsFragment()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.test, songsFragment, songsFragment.tag).commit()
        bottomSheetBehavior!!.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {

                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {

                    }
                }

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                setMiniPlayerAlphaProgress(slideOffset)
            }

        })


    }


    private fun createConnectionToService() {
        conn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                musicService = (service as MusicService.MyBinder).service
                isServiceConnection = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                musicService = null
                isServiceConnection = false
            }

        }
    }

    private fun setMiniPlayerAlphaProgress(progress: Float) {
        val alpha = 1 - progress
        miniPlayerFragment?.view?.alpha = alpha
        mBinding.bottomNav.translationY = progress * 400
        mBinding.bottomNav.alpha = alpha
        miniPlayerFragment.view?.visibility = if (alpha == 0f) View.GONE else View.VISIBLE
    }

    fun collapseBottomSheet() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun hideBottomSheet() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
    }

    fun showBottomSheet() {
        if (bottomSheetBehavior?.state == BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

}