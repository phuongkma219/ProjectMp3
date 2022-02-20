package com.phuong.mymusic.ui.mymusic.songs

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.phuong.mymusic.MusicPlayerRemote
import com.phuong.mymusic.R
import com.phuong.mymusic.base.BaseFragment
import com.phuong.mymusic.databinding.FragmentSongsBinding
import com.phuong.mymusic.helper.musicViewModel
import com.phuong.mymusic.model.Song
import com.phuong.mymusic.ui.detailsong.SendData
import com.phuong.mymusic.ui.main.MainViewModel


class SongsFragment : BaseFragment<FragmentSongsBinding>() {
    private val viewModel by viewModels<SongsViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    val musicViewModel by viewModels<musicViewModel>()
    private val songsAdapter = SongsAdapter()
    override fun getLayoutId(): Int {
        return R.layout.fragment_songs
    }

    override fun initBinding() {
        super.initBinding()
        init()
    }

    private fun init() {
        binding.rcSongs.adapter = songsAdapter
        binding.rcSongs.layoutManager =
            LinearLayoutManager(activityOwner, LinearLayoutManager.VERTICAL, false)
        viewModel.getAll(activityOwner)
        viewModel.listSongs.observe(viewLifecycleOwner) {
            songsAdapter.list = it
        }
        songsAdapter.listener = object : SongsAdapter.IMyMusic {
            override fun onItemClick(position: Int, item: Song) {
                SendData.getInstance().getLiveSong().value = item
            }
        }
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        activityOwner.supportActionBar!!.setDisplayShowHomeEnabled(true)
    }



}