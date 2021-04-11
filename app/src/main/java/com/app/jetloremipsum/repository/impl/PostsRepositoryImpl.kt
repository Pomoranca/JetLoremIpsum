package com.app.jetloremipsum.repository.impl

import com.app.jetloremipsum.data.Result
import com.app.jetloremipsum.network.ApiHelper
import com.app.jetloremipsum.network.LoremApiService
import com.app.jetloremipsum.result.Photo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) : PostsRepository
   {

     override suspend fun getPhotos(): Result<List<Photo>> {
        return apiHelper.getPhotos()
    }

//    override suspend fun getUsers(): Result<List<User>> {
//        TODO("Not yet implemented")
//    }
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


