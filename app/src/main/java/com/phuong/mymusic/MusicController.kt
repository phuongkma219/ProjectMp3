package com.phuong.mymusic

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class MusicController:MediaPlayer.OnErrorListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnBufferingUpdateListener {
    var player: MediaPlayer? = null
    var inter: IMusicController? = null
    var total = 0

    fun setData(context: Context,path:String){
        player = MediaPlayer()
        player!!.setOnErrorListener(this)
        player!!.setOnBufferingUpdateListener(this)
        player!!.setDataSource(context, Uri.parse(path))

        //dang ky prepared
        player!!.setOnPreparedListener(this)
        player!!.prepareAsync()
    }
    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
      return true
    }

    override fun onPrepared(mp: MediaPlayer) {
        total = mp.duration
        inter?.onPrepared()
        mp.start()
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
    }
    interface IMusicController{
        fun onPrepared()
    }
    fun getCurrentPosition(): Int {
        return player!!.currentPosition
    }


    fun play() {
        if (player == null) {
            return
        }
        if (player!!.isPlaying) {
            return
        }
        player!!.start()
    }

    fun stop() {
        if (player == null) {
            return
        }
        player!!.stop()
    }

    fun pause() {
        if (player == null) {
            return
        }
        player!!.pause()
    }

    fun release() {
        player?.release()
        player = null
    }
}