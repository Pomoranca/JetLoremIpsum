package com.app.jetloremipsum.repository

import com.app.jetloremipsum.network.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getResults() = apiHelper.getResults()
}