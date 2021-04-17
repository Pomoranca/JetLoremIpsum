package com.app.jetloremipsum.presentation.ui.feed

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.jetloremipsum.repository.impl.PostsRepository
import com.app.jetloremipsum.result.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedDetailsViewModel @Inject constructor(
    val repository: PostsRepository
) : ViewModel() {

    val loading = mutableStateOf(false)

    val photo: MutableState<Photo?> = mutableStateOf(null)

    suspend fun getRecipe(id: Int) {
        loading.value = true
        photo.value = repository.getPhoto(id)
        loading.value = false
    }
}


