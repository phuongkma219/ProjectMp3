package com.phuong.mymusic.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import com.phuong.mymusic.model.Song
import com.phuong.mymusic.model.lyrics.AbsSynchronizedLyrics
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey
import java.io.File
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.math.log10
import kotlin.math.pow

object MusicUtils
{
    fun createShareSongFileIntent(song: Song, context: Context): Intent? {
        return try {
            Intent().setAction(Intent.ACTION_SEND).putExtra(
                Intent.EXTRA_STREAM,
                FileProvider.getUriForFile(
                    context,
                    context.applicationContext.packageName,
                    File(song.data)
                )
            ).addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION).setType("audio/*")
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Could not share this file, I'm aware of the issue.",
                Toast.LENGTH_SHORT
            ).show()
            Intent()
        }
    }
    fun getLyrics(song: Song): String? {
        var lyrics: String? = "No lyrics found"
        val file = File(song.data)
        try {
            lyrics = AudioFileIO.read(file).tagOrCreateDefault.getFirst(FieldKey.LYRICS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (lyrics == null || lyrics.trim { it <= ' ' }.isEmpty() || !AbsSynchronizedLyrics
                .isSynchronized(lyrics)
        ) {
            val dir = file.absoluteFile.parentFile
            if (dir != null && dir.exists() && dir.isDirectory) {
                val format = ".*%s.*\\.(lrc|txt)"
                val filename = Pattern.quote(
                    FileUtil.stripExtension(file.name)
                )
                val songtitle = Pattern.quote(song.title)
                val patterns =
                    ArrayList<Pattern>()
                patterns.add(
                    Pattern.compile(
                        String.format(format, filename),
                        Pattern.CASE_INSENSITIVE or Pattern.UNICODE_CASE
                    )
                )
                patterns.add(
                    Pattern.compile(
                        String.format(format, songtitle),
                        Pattern.CASE_INSENSITIVE or Pattern.UNICODE_CASE
                    )
                )
                val files =
                    dir.listFiles { f: File ->
                        for (pattern in patterns) {
                            if (pattern.matcher(f.name).matches()) {
                                return@listFiles true
                            }
                        }
                        false
                    }
                if (files != null && files.isNotEmpty()) {
                    for (f in files) {
                        try {
                            val newLyrics =
                                FileUtil.read(f)
                            if (newLyrics != null && newLyrics.trim { it <= ' ' }.isNotEmpty()) {
                                if (AbsSynchronizedLyrics.isSynchronized(newLyrics)) {
                                    return newLyrics
                                }
                                lyrics = newLyrics
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
        return lyrics
    }

    fun getFileSize(size: Long): String? {
        if (size <= 0) return "0"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble()))
            .toString() + units[digitGroups]
    }
    fun getReadableDurationString(songDurationMillis: Long): String? {
        var minutes = songDurationMillis / 1000 / 60
        val seconds = songDurationMillis / 1000 % 60
        return if (minutes < 60) {
            String.format(
                Locale.getDefault(),
                "%02d:%02d",
                minutes,
                seconds
            )
        } else {
            val hours = minutes / 60
            minutes = minutes % 60
            String.format(
                Locale.getDefault(),
                "%02d:%02d:%02d",
                hours,
                minutes,
                seconds
            )
        }
    }

}