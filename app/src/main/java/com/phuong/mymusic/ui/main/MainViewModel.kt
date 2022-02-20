package com.phuong.mymusic.ui.main

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phuong.mymusic.base.BaseViewModel
import com.phuong.mymusic.utils.asLiveData

class MainViewModel : ViewModel {
    private var liveShowMenu = MutableLiveData<Boolean>()
    companion object {
        private const val TAG = "MainViewModel"
    }
    constructor(){
        liveShowMenu.value = true
    }


    fun showMenu() {
        liveShowMenu.value = true
    }

    fun hideMenu() {
        liveShowMenu.value =false

    }

    fun getLiveMenu() = liveShowMenu.asLiveData()
}