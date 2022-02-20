package com.phuong.mymusic.helper

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phuong.mymusic.MusicService
import com.phuong.mymusic.model.Song


class musicViewModel : ViewModel {
    private lateinit var conn: ServiceConnection
    var musicService: MusicService? = null
    var iconnect = false
    var currentSong: MutableLiveData<Song>

    constructor() {
        currentSong = MutableLiveData()

    }

    fun setData(song: Song) {
        currentSong.value = song
        Log.d("xhx", "setData: ${song.title}")

    }


    fun bind(context: Context) {
        val intent = Intent(context, MusicService::class.java)
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE)
    }

    fun createConnectionToService() {
        conn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                musicService = (service as MusicService.MyBinder).service
                iconnect = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                musicService = null
                iconnect = false
            }

        }
    }
}