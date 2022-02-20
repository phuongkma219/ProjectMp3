package com.phuong.mymusic.model

import java.io.Serializable

data class ItemSong(
    var linkImage: String,
    var songName: String,
    var artistName: String,
    var linkSong: String,
    var linkMusic: String? = null
): Serializable