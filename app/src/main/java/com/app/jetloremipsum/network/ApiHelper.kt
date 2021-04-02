package com.app.jetloremipsum.network

class ApiHelper(private val apiService: FoodApiService) {
    suspend fun getResults() = apiService.getResults()
}