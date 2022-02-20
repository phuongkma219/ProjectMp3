package com.phuong.mymusic.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseDialog(val context: Context) {

    private var mDialog: AlertDialog? = null
    private var mDialogBuilder: AlertDialog.Builder? = null
    private var parentView: View? = null

    init {
        mDialogBuilder = AlertDialog.Builder(context)
        parentView = getDialogView()
    }

    fun show() {
        val activity = context as Activity
        if (activity.isFinishing) {
            return
        }

        mDialogBuilder!!.setView(parentView)
        mDialogBuilder!!.setCancelable(isCancelable)
        if (mDialog == null) {
            mDialog = mDialogBuilder!!.create()
            mDialog!!.setCancelable(isCancelable)
            mDialog!!.setCanceledOnTouchOutside(isCancelable)
            mDialog!!.setOnDismissListener { onDismissDialog() }
        }
        try {
            val window = mDialog!!.window
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        try {
            val activity = context as AppCompatActivity
            if (activity.isFinishing) {
                return
            }
            mDialog!!.show()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        onShowingDialog()
    }

    protected abstract val isCancelable: Boolean
    protected abstract fun onDismissDialog()
    protected abstract fun onShowingDialog()
    protected abstract fun getDialogView(): View?

    val isShowing: Boolean
        get() = if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.isShowing
        } else false

    fun dismiss() {
        if (mDialog != null) {
            mDialog!!.dismiss()
        }
    }

    open fun hide() {
        try {
            (context as Activity).runOnUiThread {
                if (mDialog != null) {
                    mDialog!!.dismiss()
                }
                onDismissDialog()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getDialog(): AlertDialog? {
        return mDialog
    }
}