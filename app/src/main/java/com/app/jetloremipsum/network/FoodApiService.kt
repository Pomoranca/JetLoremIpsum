package com.app.jetloremipsum.network

import com.app.jetloremipsum.network.NetworkingConstants.URL_POSTS
import com.app.jetloremipsum.result.ItemList
import retrofit2.http.GET

interface FoodApiService {

    @GET(URL_POSTS)
    suspend fun getResults(): ItemList

}