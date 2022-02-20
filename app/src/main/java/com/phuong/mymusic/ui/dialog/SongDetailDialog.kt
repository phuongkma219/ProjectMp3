package com.phuong.mymusic.ui.dialog

import android.content.Context
import android.text.Spanned
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import com.phuong.mymusic.R
import com.phuong.mymusic.base.BaseDialogFragment
import com.phuong.mymusic.databinding.DialogFileDetailsBinding
import com.phuong.mymusic.model.Song
import com.phuong.mymusic.utils.MusicUtils
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.audio.exceptions.CannotReadException
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException
import org.jaudiotagger.tag.TagException
import java.io.File
import java.io.IOException

class SongDetailDialog: BaseDialogFragment<DialogFileDetailsBinding>() {
    val EXTRA_SONG = "extra_songs"
    override fun getLayoutId(): Int {
        return R.layout.dialog_file_details
    }

    override fun initViewBinding() {
        val context = requireContext()
        val song = requireArguments().getParcelable<Song>(EXTRA_SONG)
        mBinding.fileName.text = makeTextWithTitle(context, R.string.label_file_name, "-")
        mBinding.filePath.text =makeTextWithTitle(context, R.string.label_file_path, "-")
        mBinding.fileSize.text = makeTextWithTitle(context, R.string.label_file_size, "-")
        mBinding.fileFormat.text = makeTextWithTitle(context, R.string.label_file_format, "-")
        mBinding.trackLength.text = makeTextWithTitle(context, R.string.label_track_length, "-")
        mBinding.bitrate.text = makeTextWithTitle(context, R.string.label_bit_rate, "-")
        mBinding.samplingRate.text = makeTextWithTitle(context, R.string.label_sampling_rate, "-")
        if (song!= null){
            val songFile = File(song.data)
            if (songFile.exists()){
                mBinding.fileName.text = makeTextWithTitle(context, R.string.label_file_name, songFile.name)
                mBinding.filePath.text =makeTextWithTitle(context, R.string.label_file_path, songFile.absolutePath)
                mBinding.fileSize.text = makeTextWithTitle(context, R.string.label_file_size, MusicUtils.getFileSize(songFile.length()))
                try {
                    val audioFile = AudioFileIO.read(songFile)
                    val audioHeader = audioFile.audioHeader

                    mBinding.fileFormat.text =
                        makeTextWithTitle(context, R.string.label_file_format, audioHeader.format)
                    mBinding.trackLength.text  = makeTextWithTitle(
                        context,
                        R.string.label_track_length,
                        MusicUtils.getReadableDurationString((audioHeader.trackLength * 1000).toLong())
                    )
                    mBinding.bitrate.text = makeTextWithTitle(
                        context,
                        R.string.label_bit_rate,
                        audioHeader.bitRate + " kb/s"
                    )
                    mBinding.samplingRate.text =
                        makeTextWithTitle(
                            context,
                            R.string.label_sampling_rate,
                            audioHeader.sampleRate + " Hz"
                        )
                } catch (@NonNull e: CannotReadException) {
                    Log.e(TAG, "error while reading the song file", e)
                    // fallback
                    mBinding.trackLength.text = makeTextWithTitle(
                        context,
                        R.string.label_track_length,
                        MusicUtils.getReadableDurationString(song.duration)
                    )
                } catch (@NonNull e: IOException) {
                    Log.e(TAG, "error while reading the song file", e)
                    mBinding.trackLength.text = makeTextWithTitle(
                        context,
                        R.string.label_track_length,
                        MusicUtils.getReadableDurationString(song.duration)
                    )
                } catch (@NonNull e: TagException) {
                    Log.e(TAG, "error while reading the song file", e)
                    mBinding.trackLength.text = makeTextWithTitle(
                        context,
                        R.string.label_track_length,
                        MusicUtils.getReadableDurationString(song.duration)
                    )
                } catch (@NonNull e: ReadOnlyFileException) {
                    Log.e(TAG, "error while reading the song file", e)
                    mBinding.trackLength.text= makeTextWithTitle(
                        context,
                        R.string.label_track_length,
                        MusicUtils.getReadableDurationString(song.duration)
                    )
                } catch (@NonNull e: InvalidAudioFrameException) {
                    Log.e(TAG, "error while reading the song file", e)
                    mBinding.trackLength.text= makeTextWithTitle(
                        context,
                        R.string.label_track_length,
                        MusicUtils.getReadableDurationString(song.duration)
                    )
                }
            }
            else {
                // fallback
                mBinding.fileName.text = makeTextWithTitle(context, R.string.label_file_name, song.title)
                mBinding.trackLength.text = makeTextWithTitle(
                    context,
                    R.string.label_track_length,
                    MusicUtils.getReadableDurationString(song.duration)
                )
            }
        }
        mBinding.btnOk.setOnClickListener {
            dismiss()
        }
    }
    private fun makeTextWithTitle(context: Context, titleResId: Int, text: String?): Spanned {
        return HtmlCompat.fromHtml(
            "<b>" + context.resources.getString(titleResId) + ": " + "</b>" + text,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }
    companion object{
        private const val TAG = "SongDetailDialog"
        fun create(song: Song): SongDetailDialog {
            return SongDetailDialog().apply {
                arguments = bundleOf(
                    EXTRA_SONG to song
                )
            }
        }
    }

}