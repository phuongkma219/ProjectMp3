package com.phuong.mymusic.ui.mymusic

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.phuong.mymusic.MyApp
import com.phuong.mymusic.R
import com.phuong.mymusic.base.BaseFragment
import com.phuong.mymusic.databinding.FragmentMyMusicBinding
import com.phuong.mymusic.model.ItemMenuMusic

class MyMusicFragment : BaseFragment<FragmentMyMusicBinding>() {
    private val TAG = "MyMusicFragment"
    private val adapter = MenuMusicAdapter()
    override fun getLayoutId(): Int {
        return R.layout.fragment_my_music
    }

    override fun initBinding() {
        super.initBinding()
        initView()
    }

    private fun initView() {
        binding.rcMenuMusic.adapter = adapter
        binding.rcMenuMusic.layoutManager =
            LinearLayoutManager(activityOwner, LinearLayoutManager.VERTICAL, false)
        adapter.list = DataItemMenu.list

        adapter.listener = object : MenuMusicAdapter.IMenu {
            override fun onItemClick(position: Int, item: ItemMenuMusic) {
                when (item.title) {
                    MyApp.resource().getString(R.string.songs) -> {
                        findNavController().navigate(R.id.action_myMusicFragment_to_songsFragment)
                    }
                }
            }

        }
    }

}