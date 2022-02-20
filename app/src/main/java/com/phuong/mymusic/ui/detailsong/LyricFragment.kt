package com.phuong.mymusic.ui.detailsong

import androidx.lifecycle.Observer
import com.phuong.mymusic.R
import com.phuong.mymusic.base.BaseFragment
import com.phuong.mymusic.databinding.FragmentLyricBinding
import com.phuong.mymusic.utils.MusicUtils

class LyricFragment: BaseFragment<FragmentLyricBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_lyric
    }


    override fun initBinding() {
        super.initBinding()
        binding.NestedScrollView.isNestedScrollingEnabled = false
        SendData.getInstance().getLiveSong().observe(this, Observer {
            val song = it
            binding.item = song
            binding.tvLyric.text = MusicUtils.getLyrics(song)
        })
    }
}