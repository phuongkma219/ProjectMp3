package com.phuong.mymusic.ui.mymusic

import com.phuong.mymusic.R
import com.phuong.mymusic.model.ItemMenuMusic
import com.phuong.newspaper.base.BaseAdapter
import com.phuong.newspaper.base.BaseListener

class MenuMusicAdapter:BaseAdapter<ItemMenuMusic>(R.layout.item_menu_music) {
    interface IMenu:BaseListener{
        fun onItemClick(position:Int,item: ItemMenuMusic)
    }
}