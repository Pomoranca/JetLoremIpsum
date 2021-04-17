package com.app.jetloremipsum.repository.impl

import com.app.jetloremipsum.result.Photo

interface PostsRepository {

    suspend fun getPhotos(number: Int): List<Photo>

    suspend fun getPhoto(id : Int): Photo


}