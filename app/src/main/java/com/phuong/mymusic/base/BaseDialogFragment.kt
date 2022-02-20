package com.phuong.mymusic.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment<VB:ViewDataBinding>:DialogFragment() {
    private lateinit var dialogView: View
    protected lateinit var mBinding: VB

    @Suppress("UNREACHABLE_CODE")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
         val dialogFragment =  super.onCreateDialog(savedInstanceState)
        dialogView = LayoutInflater.from(context).inflate(getLayoutId(), null)
        mBinding = DataBindingUtil.bind(dialogView)!!
        initViewBinding()
        dialogFragment.setContentView(dialogView)
        return dialogFragment
    }

    abstract fun initViewBinding()

    abstract fun getLayoutId(): Int

}