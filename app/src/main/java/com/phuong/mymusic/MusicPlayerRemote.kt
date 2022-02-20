package com.phuong.mymusic

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.MutableLiveData
import com.phuong.mymusic.model.Song
import org.koin.core.KoinComponent

object MusicPlayerRemote : KoinComponent {
    private lateinit var conn: ServiceConnection
    var musicService: MusicService? = null
    @JvmStatic
    val isPlaying: Boolean
        get() = musicService != null && musicService!!.isPlaying()


    fun createService(context: Context){
        val intent = Intent(context, MusicService::class.java)
        context.startService(intent)
    }

    fun createConnectionToService(context: Context) {
        conn = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                musicService = null
            }

            override fun onServiceConnected(
                name: ComponentName?,
                service: IBinder
            ) {
                musicService = (service as MusicService.MyBinder).service
            }
        }
        val intent = Intent()
        intent.setClass(context, MusicService::class.java)
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE)
    }
    fun setDataSource(song: Song){
        musicService?.setDataSource(song)
    }
    fun onPlay(){
        musicService?.playMusic()
    }
    fun onStop(){
        musicService?.stopMusic()
    }

}