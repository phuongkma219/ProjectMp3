package com.phuong.mymusic.ui.home

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.phuong.mymusic.R
import com.phuong.mymusic.base.BaseFragment
import com.phuong.mymusic.databinding.FragmentHomeBinding
import com.phuong.mymusic.helper.musicViewModel
import com.phuong.mymusic.model.Song
import com.phuong.mymusic.ui.main.MainViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val mainViewModel by activityViewModels<MainViewModel>()
    lateinit var musicViewModel : musicViewModel
    private val TAG = "HomeFragment"
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }


    override fun initBinding() {
        super.initBinding()
        musicViewModel =musicViewModel()
        binding.btnTest.setOnClickListener {
//            val model = SongsViewModel()
//            model.getAll(activityOwner)
//            var song: Song? = null
//            model.list.observe(viewLifecycleOwner, Observer {
//                song = it[0]
//            })
//            CoroutineScope(Dispatchers.Default).launch {
//                Log.d(TAG, "initBinding: " + MusicUtils.getLyrics(song!!))
//            }


            Log.d(TAG, "initBinding: ${musicViewModel.currentSong.value?.title}")
        }

    }

}