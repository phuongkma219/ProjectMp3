package com.phuong.mymusic.ui.mymusic.songs

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.phuong.mymusic.base.BaseViewModel
import com.phuong.mymusic.model.Song
import com.phuong.mymusic.repository.SongRepository
import com.phuong.mymusic.utils.asLiveData
import kotlinx.coroutines.launch

class SongsViewModel : BaseViewModel() {
    private val _listSongs = MutableLiveData<MutableList<Song>>()
    val listSongs = _listSongs.asLiveData()

    fun getAll(context: Context){
        viewModelScope.launch {
            _listSongs.value = SongRepository.getListAudio(context)
        }
    }
}