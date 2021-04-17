package com.app.jetloremipsum.di

import android.content.Context
import com.app.jetloremipsum.BaseApplication
import com.app.jetloremipsum.network.LoremApiService
import com.app.jetloremipsum.network.NetworkingConstants
import com.app.jetloremipsum.repository.impl.PostsRepository
import com.app.jetloremipsum.repository.impl.PostsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(NetworkingConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): LoremApiService =
        retrofit.create(LoremApiService::class.java)


}