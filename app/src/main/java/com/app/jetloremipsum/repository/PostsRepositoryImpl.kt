package com.app.jetloremipsum.repository

import com.app.jetloremipsum.network.ApiHelper
import com.app.jetloremipsum.result.Photo
import com.app.jetloremipsum.result.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) : PostsRepository
   {

     override suspend fun getPhotos(number: Int): List<Photo> {
        return apiHelper.getPhotos(number)
    }

       override suspend fun getPhoto(id: Int): Photo {
           return apiHelper.getPhoto(id)
       }

    override suspend fun getUsers(): List<User> {
        return apiHelper.getUsers()
    }

       override suspend fun getUser(id: Int): User {
           return apiHelper.getUser(id)
       }
//
//    override suspend fun getComments(): Result<List<Comment>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getAlbums(): Result<List<Album>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getPosts(): Result<List<Post>> {
//        TODO("Not yet implemented")
//    }


}


