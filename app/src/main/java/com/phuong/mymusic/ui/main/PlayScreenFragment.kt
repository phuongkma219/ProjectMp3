//package com.phuong.mymusic.ui.main
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.Fragment
//import com.google.android.material.bottomsheet.BottomSheetBehavior
//import com.phuong.mymusic.R
//import com.phuong.mymusic.databinding.SlidingMusicPanelLayoutBinding
//import com.phuong.mymusic.ui.MiniPlayerFragment
//
//class PlayScreenFragment : Fragment() {
//    companion object {
//        const val TAG = "PlayScreenFragment"
//        fun newInstance(): PlayScreenFragment {
//            val args = Bundle()
//            val playScreenFragment = PlayScreenFragment()
//            playScreenFragment.arguments = args
//            return playScreenFragment
//        }
//    }
//
//    lateinit var fragmentPlayerBinding: SlidingMusicPanelLayoutBinding
//    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
//    private lateinit var miniPlayerFragment: MiniPlayerFragment
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        fragmentPlayerBinding =
//            DataBindingUtil.inflate(inflater, R.layout.sliding_music_panel_layout, container, false)
//        bottomSheetBehavior = BottomSheetBehavior.from(fragmentPlayerBinding.botomSheet)
//        miniPlayerFragment =
//            childFragmentManager.findFragmentById(R.id.player_song_mini) as MiniPlayerFragment
//        fragmentPlayerBinding.botomSheet.setOnClickListener {
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//            miniPlayerFragment.view?.visibility = View.GONE
//        }
//        bottomSheetBehavior.addBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState) {
//                    BottomSheetBehavior.STATE_COLLAPSED -> {
//                        Log.d(TAG, "onStateChanged: coll")
//                    }
//                    BottomSheetBehavior.STATE_EXPANDED -> {
//                        Log.d(TAG, "onStateChanged: ex")
//                    }
//                }
//
//            }
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                Log.d(TAG, "onSlide: $slideOffset")
//                setMiniPlayerAlphaProgress(slideOffset)
//            }
//
//        })
//
//
//
//        return fragmentPlayerBinding.root
//    }
//
//    private fun setMiniPlayerAlphaProgress(progress: Float) {
//        val alpha = 1 - progress
//        Log.d(PlayScreenFragment.TAG, "setMiniPlayerAlphaProgress: $alpha")
////        miniPlayerFragment.view?.alpha = alpha
//        miniPlayerFragment.view?.visibility = if (alpha == 0f) View.GONE else View.VISIBLE
//    }
//}