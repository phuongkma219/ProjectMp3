package com.phuong.mymusic.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import com.phuong.mymusic.MyApp
import com.phuong.mymusic.R
import com.phuong.mymusic.model.Song
import com.phuong.mymusic.ui.dialog.SongDetailDialog
import com.phuong.mymusic.ui.main.MainActivity

class SongAction(val context: Context, val view: View) : PopupMenu(context, view) {
    val MENU_RES = R.menu.menu_item_song
    private lateinit var song: Song
    fun setMenu() {
        menuInflater.inflate(MENU_RES, menu)
        setOnMenuItemClickListener {
            return@setOnMenuItemClickListener menuItemClicked(it)
        }
        show()
    }
    fun setSong(song: Song){
        this.song = song
    }

    private fun menuItemClicked(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_details -> {
                SongDetailDialog.create(song).show((context as MainActivity).supportFragmentManager,"SONG_DETAIL")
            }
        }
        return true
    }
}