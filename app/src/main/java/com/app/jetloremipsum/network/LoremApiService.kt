package com.app.jetloremipsum.network

import com.app.jetloremipsum.data.Result
import com.app.jetloremipsum.network.NetworkingConstants.URL_PHOTOS
import com.app.jetloremipsum.network.NetworkingConstants.URL_POSTS
import com.app.jetloremipsum.result.Photo
import com.app.jetloremipsum.result.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface LoremApiService {

    @GET(URL_POSTS)
    suspend fun getResults(): List<Post>

    @GET("$URL_POSTS/{number}")
    suspend fun getResult(@Path("number") number: Int): Result<Post>

    @GET(URL_PHOTOS)
    suspend fun getPhotos(): Result<List<Photo>>


}