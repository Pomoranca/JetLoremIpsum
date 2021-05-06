package com.app.jetloremipsum.network

import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: LoremApiService) {

    suspend fun getPhotos(number : Int) = apiService.getPhotos(number)

    suspend fun getPhoto(id: Int) = apiService.getPhoto(id)

    suspend fun getUsers() = apiService.getUsers()

    suspend fun getUser(id: Int) = apiService.getUser(id)
}