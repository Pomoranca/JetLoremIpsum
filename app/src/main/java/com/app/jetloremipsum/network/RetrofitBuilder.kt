package com.app.jetloremipsum.network

import com.app.jetloremipsum.network.NetworkingConstants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: FoodApiService = getRetrofit().create(FoodApiService::class.java)
}