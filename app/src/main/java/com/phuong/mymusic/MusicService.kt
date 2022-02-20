package com.phuong.mymusic

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.phuong.mymusic.model.Song
import android.app.NotificationChannel
import com.phuong.mymusic.ui.main.MainActivity


class MusicService : Service(), MusicController.IMusicController {
    private val TAG = "MusicService"
    private lateinit var mMusicController: MusicController
    var inter: MusicController.IMusicController? = null

    class MyBinder : Binder {
        val service: MusicService

        constructor(service: MusicService) {
            this.service = service
        }
    }

    override fun onCreate() {
        super.onCreate()
        mMusicController = MusicController()
        mMusicController.inter = this
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind: MusicService")
        return MyBinder(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    fun setDataSource(song: Song) {
        mMusicController.setData(this, song.data)
        createNotificationChannel(song)
    }

    fun playMusic() {
        mMusicController.play()
    }

    fun stopMusic() {
        mMusicController.stop()
    }

    private fun createNotificationChannel(song: Song) {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "createNotificationChannel: vao day")
            val name = "demo1"
            val descriptionText = "descriptionText"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            manager.createNotificationChannel(channel)
        }

        val action = Intent(this, MainActivity::class.java)
        action.putExtra("abc", true )
        action.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val clickIntent = PendingIntent
            .getActivity(this, 0, action, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationLayout = getCombinedRemoteViews(true, song)
        val notificationLayoutBig = getCombinedRemoteViews(false, song)

        val notification = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.baseline_music_note_white_24dp)
            .setAutoCancel(true)
            .setShowWhen(false)
            .setCustomBigContentView(notificationLayoutBig)
            .setCustomContentView(notificationLayout)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)


        Glide.with(this)
            .asBitmap()
            .load("content://media/external/audio/albumart/" + song.albumId)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    notificationLayout.setImageViewBitmap(R.id.largeIcon, resource)
                    notificationLayoutBig.setImageViewBitmap(R.id.largeIcon, resource)
                    manager.notify(1, notification.build())

                }

            })
        }




    private fun getCombinedRemoteViews(collapsed: Boolean, song: Song): RemoteViews {
        val remoteViews = RemoteViews(
            packageName,
            if (collapsed) R.layout.layout_notification_collapsed else R.layout.layout_notification_expanded
        )

        remoteViews.setTextViewText(
            R.id.appName,
            getString(R.string.app_name) + " • " + song.albumName
        )
        remoteViews.setTextViewText(R.id.title, song.title)
        remoteViews.setTextViewText(R.id.subtitle, song.artistName)

        return remoteViews
    }
    fun getTotalTime():Int{
        return mMusicController.total
    }
    fun isPlaying() :Boolean{
        return ((mMusicController.player!= null) && mMusicController.player!!.isPlaying)
    }
    fun getCurrentPosition():Int{
        return mMusicController.getCurrentPosition()
    }



    override fun onDestroy() {
        Log.d(TAG, "onDestroy: MyService")
        super.onDestroy()
    }

    override fun onPrepared() {

    }
}