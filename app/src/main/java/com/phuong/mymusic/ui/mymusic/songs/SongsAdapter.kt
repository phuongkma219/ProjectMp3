package com.phuong.mymusic.ui.mymusic.songs

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.phuong.mymusic.R
import com.phuong.mymusic.databinding.ItemSongLocalBinding
import com.phuong.mymusic.model.ItemSong
import com.phuong.mymusic.model.Song
import com.phuong.mymusic.utils.SongAction
import com.phuong.newspaper.base.BaseAdapter
import com.phuong.newspaper.base.BaseListener
import com.phuong.newspaper.base.BaseViewHolder


open class SongsAdapter: BaseAdapter<Song>(R.layout.item_song_local) {
    interface IMyMusic:BaseListener{
        fun onItemClick(position: Int,item:Song)

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = holder.binding as ItemSongLocalBinding
       item.ivMoreOption.setOnClickListener {
          val songsAction = SongAction(item.ivMoreOption.context,item.ivMoreOption)
           songsAction.setSong(item.item as Song)
           songsAction.setMenu()
       }
    }
}