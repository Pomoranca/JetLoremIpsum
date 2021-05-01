package com.app.jetloremipsum.repository

import com.app.jetloremipsum.result.Photo
import com.app.jetloremipsum.result.User
import com.app.jetloremipsum.ui.feed.Result

interface PostsRepository {

    suspend fun getPhotos(number: Int): List<Photo>

    suspend fun getPhoto(id : Int): Photo

    suspend fun getUsers() : List<User>

    suspend fun getUser(id: Int) : Result<User>


}