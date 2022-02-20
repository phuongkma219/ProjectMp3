package com.phuong.mymusic.ui.detailsong

import androidx.lifecycle.MutableLiveData
import com.phuong.mymusic.model.Song

class SendData private constructor(){
    private var song :MutableLiveData<Song> = MutableLiveData()
    fun getLiveSong():MutableLiveData<Song>{
        return song
    }
    companion object{
        private val mInstance = SendData()
        @Synchronized
        fun getInstance():SendData{
            return mInstance
        }
    }
}