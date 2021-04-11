package com.app.jetloremipsum.repository.impl

import com.app.jetloremipsum.data.Result
import com.app.jetloremipsum.result.Photo

interface PostsRepository {

    suspend fun getPhotos(): Result<List<Photo>>


}