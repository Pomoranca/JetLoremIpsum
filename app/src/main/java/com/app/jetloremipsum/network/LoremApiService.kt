package com.app.jetloremipsum.network

import com.app.jetloremipsum.network.NetworkingConstants.URL_ALBUMS
import com.app.jetloremipsum.network.NetworkingConstants.URL_PHOTOS
import com.app.jetloremipsum.network.NetworkingConstants.URL_POSTS
import com.app.jetloremipsum.network.NetworkingConstants.URL_USERS
import com.app.jetloremipsum.result.Photo
import com.app.jetloremipsum.result.Post
import com.app.jetloremipsum.result.User
import retrofit2.http.GET
import retrofit2.http.Path

interface LoremApiService {

    @GET(URL_POSTS)
    suspend fun getResults(): List<Post>

    @GET("$URL_POSTS/{number}")
    suspend fun getResult(@Path("number") number: Int): Post

    @GET("$URL_ALBUMS/{number}/$URL_PHOTOS")
    suspend fun getPhotos(@Path("number") number: Int): List<Photo>

    @GET("$URL_PHOTOS/{id}")
    suspend fun getPhoto(@Path("id") id: Int): Photo

    @GET(URL_USERS)
    suspend fun getUsers(): List<User>

    @GET("$URL_USERS/{id}")
    suspend fun getUser(@Path("id") id: Int): User


}