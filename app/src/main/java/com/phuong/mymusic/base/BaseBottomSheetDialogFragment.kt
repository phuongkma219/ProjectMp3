package com.phuong.mymusic.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.phuong.mymusic.ui.main.MainActivity

abstract class BaseBottomSheetDialogFragment<VB : ViewDataBinding> : BottomSheetDialogFragment() {
    protected lateinit var mBinding: VB
    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    protected val activityOwner by lazy {
        requireActivity() as MainActivity
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomsheet = super.onCreateDialog(savedInstanceState)
        val view = View.inflate(requireActivity(), getLayoutId(), null)
        mBinding = DataBindingUtil.bind(view)!!
        bottomsheet.setContentView(view)
        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        setupFullHeight(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        initBinding()
        return bottomsheet
    }
    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    abstract fun initBinding()
    abstract fun getLayoutId(): Int

}