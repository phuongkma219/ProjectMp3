package com.phuong.mymusic.ui.detailsong

import android.util.Log
import androidx.lifecycle.Observer
import com.phuong.mymusic.R
import com.phuong.mymusic.base.BaseFragment
import com.phuong.mymusic.databinding.LayoutDemoBinding

class DemoFragment : BaseFragment<LayoutDemoBinding>() {
    companion object{
        private const val TAG = "DemoFragment"
    }
    override fun getLayoutId(): Int {
        return R.layout.layout_demo

    }

    override fun initBinding() {
        super.initBinding()
        binding.tvSongName.isSelected = true
        binding.cvImageSong.clipToOutline = true
       SendData.getInstance().getLiveSong().observe(this, Observer { 
           binding.item = it
           Log.d(TAG, "initBinding: $it")
       })
    }
}