package com.phuong.mymusic.ui

import android.os.AsyncTask
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.phuong.mymusic.MusicPlayerRemote
import com.phuong.mymusic.R
import com.phuong.mymusic.base.BaseFragment
import com.phuong.mymusic.databinding.FragmentMiniPlayerBinding
import com.phuong.mymusic.helper.musicViewModel
import com.phuong.mymusic.model.Song
import com.phuong.mymusic.ui.detailsong.SendData
import com.phuong.mymusic.ui.main.MainViewModel
import com.phuong.mymusic.utils.loadImage
import java.text.SimpleDateFormat

class MiniPlayerFragment : BaseFragment<FragmentMiniPlayerBinding>(), View.OnClickListener {
    private val TAG = "ControllerFragment"
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val musicViewModel by viewModels<musicViewModel>()
    private var isRunning = false
    private var song: Song? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_mini_player
    }

    override fun initBinding() {
        super.initBinding()
        binding.tvMiniSongName.isSelected = true
        binding.icActionNext.setOnClickListener(this)
        binding.icActionPlay.setOnClickListener(this)
        binding.icActionPrevious.setOnClickListener(this)
        musicViewModel.currentSong.observe(this.requireActivity(), Observer {
            binding.tvMiniSongName.text = musicViewModel.currentSong.value!!.title.toString()
        })

        SendData.getInstance().getLiveSong().observe(this, Observer {
            val song = it
            binding.tvMiniSongName.text = song?.title
            binding.tvMiniArtisName.text = song?.artistName
            loadImage(binding.imgIcon, song?.albumId)
            binding.icActionPlay.setImageResource(R.drawable.ic_baseline_pause_24)
            isRunning = true
            MusicPlayerRemote.setDataSource(song)
            MusicPlayerRemote.onPlay()
        })

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ic_action_play -> {
                if (isRunning) {
                    binding.icActionPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    MusicPlayerRemote.onStop()
                    isRunning = false
                } else {
                    binding.icActionPlay.setImageResource(R.drawable.ic_baseline_pause_24)
                    isRunning = true
                }

            }
            R.id.ic_action_next -> {
                Toast.makeText(activityOwner, "Update later", Toast.LENGTH_SHORT).show()

            }
            R.id.ic_action_previous -> {
                Toast.makeText(activityOwner, "Update later", Toast.LENGTH_SHORT).show()

            }
        }
    }


}