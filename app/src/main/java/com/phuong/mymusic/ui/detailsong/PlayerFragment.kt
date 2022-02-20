package com.phuong.mymusic.ui.detailsong

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.phuong.mymusic.MusicPlayerRemote
import com.phuong.mymusic.R
import com.phuong.mymusic.base.BaseFragment
import com.phuong.mymusic.databinding.FragmentSongDetailBinding
import com.phuong.mymusic.helper.musicViewModel
import com.phuong.mymusic.model.Song
import com.phuong.mymusic.ui.main.MainViewModel
import com.phuong.mymusic.utils.*
import com.phuong.mymusic.utils.MusicUtils.getReadableDurationString
import java.io.File

class PlayerFragment : BaseFragment<FragmentSongDetailBinding>(),
    View.OnClickListener {
    private val TAG = "SongDetailFragment"
    private val mainViewModel by activityViewModels<MainViewModel>()
    val musicViewModel by viewModels<musicViewModel>()

    private lateinit var itemSong: Song
    override fun getLayoutId(): Int {
        return R.layout.fragment_song_detail
    }

    override fun initBinding() {
        binding.ivClose.setOnClickListener(this)
        binding.ivAcShare.setOnClickListener(this)
        binding.icAcPlay.setOnClickListener(this)
        val viewPagerAdapter = ViewPagerAdapter(
            childFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        binding.viewPager2.adapter = viewPagerAdapter
        binding.cricleIndicator.setViewPager(binding.viewPager2)
        SendData.getInstance().getLiveSong().observe(this, Observer {
            itemSong = it
            binding.item = itemSong
            binding.tvEnd.text = getReadableDurationString(itemSong!!.duration)
        })
//        MusicPlayerRemote.isPlay()
        Log.d(TAG, "initBinding: ko vao day ${MusicPlayerRemote.isPlaying}")






        }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_close -> {
                mainViewModel.showMenu()
                activityOwner.showBottomSheet()

            }
            R.id.iv_ac_share -> {
                Log.d(TAG, "onClick: ")
                FileUtils.sendfile(activityOwner)
            }
            R.id.ic_ac_play -> {
                Log.d(TAG, "onClick: ")
                val mediaPlayer =
                    MediaPlayer.create(activityOwner, Uri.fromFile(File(itemSong.data)))
                mediaPlayer.setLooping(true)
                mediaPlayer.start()

            }

        }
    }


}