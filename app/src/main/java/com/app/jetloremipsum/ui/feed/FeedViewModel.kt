package com.app.jetloremipsum.ui.feed

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.jetloremipsum.repository.PostsRepository
import com.app.jetloremipsum.result.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: PostsRepository,
    ) : ViewModel() {

    val photos: Flow<PagingData<Photo>> = Pager(PagingConfig(pageSize = 30)) {
        PhotoSource(repository)
    }.flow


}



