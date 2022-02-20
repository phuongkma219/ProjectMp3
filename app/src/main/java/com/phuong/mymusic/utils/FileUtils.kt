package com.phuong.mymusic.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.phuong.mymusic.model.ItemSong
import com.phuong.mymusic.model.Song


object FileUtils {
    private val TAG = "FileUtils"


    fun getListAudio(context: Context): MutableList<ItemSong> {
        val audios = ArrayList<ItemSong>()

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
        )

        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        try {
            cursor?.let {
                var hasRow = it.moveToFirst()
                while (hasRow) {
                    val indexImg = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID)
                    val name =
                        it.getString(it.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val artist: String =
                        it.getString(it.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val filePath = it.getString(it.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val duration = it.getString(it.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val albumId = cursor.getLong(indexImg)
                    val albumArtUri = "content://media/external/audio/albumart/" + albumId
                    Log.d(TAG, "getListAudio: $albumArtUri")
                    if (!artist.equals("<unknown>")){
                        audios.add(ItemSong(albumArtUri, name, artist, filePath, duration))
                    }
                    hasRow = it.moveToNext()
                }
            }
        } finally {
            cursor?.close()
        }
        return audios
    }

    fun sendfile(context: Context) {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://developer.android.com/training/sharing/")
            type = "text/plain"
            // (Optional) Here we're setting the title of the content
//            putExtra(Intent.EXTRA_TITLE, "Introducing content previews")
            
        }, null)
        (context as Activity).startActivityForResult(share, 1001)
    }
}