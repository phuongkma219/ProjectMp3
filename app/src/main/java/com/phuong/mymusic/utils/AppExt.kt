package com.phuong.mymusic.utils

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.phuong.mymusic.R

fun DialogFragment.materialDialog(title: Int): MaterialAlertDialogBuilder {
    return MaterialAlertDialogBuilder(
        requireContext(),
        R.style.MaterialAlertDialog_MaterialComponents
    ).setTitle(title)
}
