package com.app.jetloremipsum.presentation.ui.feed

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.jetloremipsum.repository.impl.PostsRepository
import com.app.jetloremipsum.result.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FeedDetailsViewModel @Inject constructor(
    val repository: PostsRepository
) : ViewModel() {

    val _photo = MutableLiveData<Photo>()

    val photo: LiveData<Photo>
        get() = _photo

    val loading = mutableStateOf(true)

    suspend fun getFeedItem(id: Int) {
        Log.i("PHOTOS", "STARTING GET FEED ITEM")
        delay(1000)
        withContext(viewModelScope.coroutineContext) {
            _photo.value = repository.getPhoto(id)
        }
        loading.value = false

    }


}


