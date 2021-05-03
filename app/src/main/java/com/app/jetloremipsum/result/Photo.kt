package com.app.jetloremipsum.result

data class Photo(
    val albumId: Int = -1,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)