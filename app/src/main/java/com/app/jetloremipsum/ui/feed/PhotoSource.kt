package com.app.jetloremipsum.ui.feed

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.jetloremipsum.repository.PostsRepository
import com.app.jetloremipsum.result.Photo
import kotlinx.coroutines.delay
import java.lang.Exception
import javax.inject.Inject

class PhotoSource
@Inject constructor(
    val repository: PostsRepository,
) : PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        delay(500)
        return try {
            val nextPage = params.key ?: 1
            val photoListResponse = repository.getPhotos(nextPage)

            LoadResult.Page(
                data = photoListResponse,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}