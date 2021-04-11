package com.app.jetloremipsum.presentation.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.jetloremipsum.data.Result
import com.app.jetloremipsum.repository.impl.PostsRepositoryImpl
import com.app.jetloremipsum.result.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FeedViewModel @Inject constructor(
     val repositoryImpl: PostsRepositoryImpl
) : ViewModel() {

    private val _photos = MutableLiveData<Result<List<Photo>>>()

    val photos: LiveData<Result<List<Photo>>>
        get() = _photos


    init {
        getPhotos()
    }

    private fun getPhotos() {
        CoroutineScope(Dispatchers.IO).launch {
            _photos.value = repositoryImpl.getPhotos()
        }
    }


}

