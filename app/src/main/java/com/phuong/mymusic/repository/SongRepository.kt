package com.phuong.mymusic.repository

import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.BaseColumns
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.database.getStringOrNull
import com.phuong.mymusic.extensions.getInt
import com.phuong.mymusic.extensions.getLong
import com.phuong.mymusic.extensions.getString
import com.phuong.mymusic.extensions.getStringOrNull
import com.phuong.mymusic.model.Song

object SongRepository{
    private val TAG = "SongRepository"
    private fun getSongFromCursorImpl(
        cursor: Cursor
    ): Song {
        val id = cursor.getLong(MediaStore.Audio.AudioColumns._ID)
        val title = cursor.getString(MediaStore.Audio.AudioColumns.TITLE)
        val trackNumber = cursor.getInt(MediaStore.Audio.AudioColumns.TRACK)
        val year = cursor.getInt(MediaStore.Audio.AudioColumns.YEAR)
        val duration = cursor.getLong(MediaStore.Audio.AudioColumns.DURATION)
        val data = cursor.getString(MediaStore.Audio.AudioColumns.DATA)
        val dateModified = cursor.getLong(MediaStore.Audio.AudioColumns.DATE_MODIFIED)
        val albumId = cursor.getLong(MediaStore.Audio.AudioColumns.ALBUM_ID)
        val albumName = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.ALBUM)
        val artistId = cursor.getLong(MediaStore.Audio.AudioColumns.ARTIST_ID)
        val artistName = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.ARTIST)
        val composer = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.COMPOSER)
        val albumArtist = cursor.getStringOrNull("album_artist")
        return Song(
            id,
            title,
            trackNumber,
            year,
            duration,
            data,
            dateModified,
            albumId,
            albumName ?: "",
            artistId,
            artistName ?: "",
            composer ?: "",
            albumArtist ?: ""
        )
    }

    fun getListAudio(context: Context): MutableList<Song> {
        val audios = ArrayList<Song>()

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            BaseColumns._ID, // 0
            MediaStore.Audio.AudioColumns.TITLE, // 1
            MediaStore.Audio.AudioColumns.TRACK, // 2
            MediaStore.Audio.AudioColumns.YEAR, // 3
            MediaStore.Audio.AudioColumns.DURATION, // 4
            MediaStore.Audio.AudioColumns.DATA, // 5
            MediaStore.Audio.AudioColumns.DATE_MODIFIED, // 6
            MediaStore.Audio.AudioColumns.ALBUM_ID, // 7
            MediaStore.Audio.AudioColumns.ALBUM, // 8
            MediaStore.Audio.AudioColumns.ARTIST_ID, // 9
            MediaStore.Audio.AudioColumns.ARTIST, // 10
            MediaStore.Audio.AudioColumns.COMPOSER, // 11
            "album_artist" // 12
        )

        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        try {
            cursor?.let {
                var hasRow = it.moveToFirst()
                while (hasRow) {
                    audios.add(getSongFromCursorImpl(it))
                    hasRow = it.moveToNext()
                }
            }
        } finally {
            cursor?.close()
        }
        return audios
    }

}