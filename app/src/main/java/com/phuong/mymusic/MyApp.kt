package com.phuong.mymusic

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.phuong.mymusic.base.BaseApplication
import com.phuong.mymusic.base.resoure.BaseResource

class MyApp :BaseApplication() {
    private val TAG = "MyApp"

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var resource: BaseResource
        fun resource() = resource
    }

    override fun onCreate() {
        super.onCreate()
        resource = BaseResource(this)
//        createNotification()
    }
    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("KEY_ID","Service Example", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            if (manager!= null){
                manager.createNotificationChannel(channel)
            }
        }
    }
}