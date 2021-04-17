package com.app.jetloremipsum.di

import com.app.jetloremipsum.network.ApiHelper
import com.app.jetloremipsum.network.LoremApiService
import com.app.jetloremipsum.repository.impl.PostsRepository
import com.app.jetloremipsum.repository.impl.PostsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        apiHelper: ApiHelper
    ) : PostsRepository {
        return PostsRepositoryImpl(
            apiHelper = apiHelper
        )
    }

}