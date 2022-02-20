package com.phuong.mymusic.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.phuong.mymusic.MyApp
import com.phuong.mymusic.R
import jp.wasabeef.blurry.Blurry
import java.io.File

private val TAG = "Utils"

@BindingAdapter("loadImageFromResoure")
fun loadImageFromResoure(iv: ImageView, image: Int) {
    iv.setImageResource(image)
}

@BindingAdapter("updateText")
fun updateText(tv: TextView, string: String) {
    tv.text = string
}

@BindingAdapter("loadImageFromPath")
fun loadImageFromPath(img: ImageView, albumId: Long) {
    val path = "content://media/external/audio/albumart/" + albumId
    val file = File(path)
    val bitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
    img.setImageBitmap(bitmap)
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

@BindingAdapter("loadImage")
fun loadImage(iv: ImageView, albumId: Long?) {
    if (albumId == null) {
        iv.setImageResource(R.drawable.ic_file_audio)
    } else {
        Glide.with(iv)
            .load("content://media/external/audio/albumart/" + albumId)
            .placeholder(R.drawable.ic_file_audio)
            .error(R.drawable.aodai)
            .into(iv)
    }
}

@BindingAdapter("loadImageBackgroud")
fun loadImageBackgroud(iv: ImageView, albumId: Long?) {
    if (albumId == null) {
        val bitmap = (MyApp.resource().getDrawable(R.drawable.aodai) as BitmapDrawable).bitmap
        Blurry.with(iv.context).from(bitmap).into(iv)

    } else {
        Glide.with(iv)
            .asBitmap()
            .load("content://media/external/audio/albumart/" + albumId)
            .error(R.mipmap.ic_launcher)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Blurry.with(iv.context).from(resource).into(iv)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }
}

@BindingAdapter("loadImageBgLyric")
fun loadImageBgLyric(iv: ImageView, albumId: Long?) {
    if (albumId == null) {
        val bitmap = (MyApp.resource().getDrawable(R.drawable.aodai) as BitmapDrawable).bitmap
        Blurry.with(iv.context).from(bitmap).into(iv)

    } else {
        Glide.with(iv)
            .asBitmap()
            .load("content://media/external/audio/albumart/" + albumId)
            .error(R.mipmap.ic_launcher)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Blurry.with(iv.context)
                        .color(Color.argb(240, 255, 255, 255))
                        .from(resource)
                        .into(iv)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }
}
