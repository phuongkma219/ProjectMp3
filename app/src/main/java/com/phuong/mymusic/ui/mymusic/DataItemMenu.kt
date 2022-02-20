package com.phuong.mymusic.ui.mymusic

import com.phuong.mymusic.MyApp
import com.phuong.mymusic.R
import com.phuong.mymusic.model.ItemMenuMusic

object DataItemMenu {
    var list = mutableListOf<ItemMenuMusic>(
        ItemMenuMusic(R.drawable.ic_baseline_queue_music_24,MyApp.resource().getString(R.string.songs)),
        ItemMenuMusic(R.drawable.ic_baseline_favorite_border,MyApp.resource().getString(R.string.favorite)),
//        ItemMenuMusic(R.drawable.ic_baseline_arrow_downward_24,MyApp.resource().getString(R.string.download))
    )
}