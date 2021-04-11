package com.app.jetloremipsum.network

import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: LoremApiService) {

    suspend fun getResults() = apiService.getResults()

    suspend fun getPhotos() = apiService.getPhotos()
}