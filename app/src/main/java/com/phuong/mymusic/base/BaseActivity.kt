package com.phuong.newspaper.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

abstract class  BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var mBinding: VB

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,getLayoutId())
        init()
    }
}