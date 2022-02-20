package com.phuong.mymusic.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phuong.mymusic.R

class DemoActivity:AppCompatActivity() {
//    private var playScreenFragment = PlayScreenFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        setupPlayScreenFragment()
        val a = arrayListOf<Int>(10)
    }

    private fun setupPlayScreenFragment() {
        supportFragmentManager
            .beginTransaction()
//            .replace(R.id.play_screen_frame_layout, playScreenFragment, PlayScreenFragment.TAG)
            .commitAllowingStateLoss()
    }
}