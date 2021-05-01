package com.app.jetloremipsum.ui.feed

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.jetloremipsum.repository.PostsRepository
import com.app.jetloremipsum.result.Photo
import com.app.jetloremipsum.utils.UIResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject


sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

@HiltViewModel
class FeedDetailsViewModel @Inject constructor(
    val repository: PostsRepository
) : ViewModel() {

    val _photo = MutableLiveData<Photo>()

    val photo: LiveData<Photo>
        get() = _photo

    val _viewState = MutableLiveData<UIResponseState>()

    val viewState: LiveData<UIResponseState>
        get() = _viewState


    fun getFeedItem(id: Int) {
        viewModelScope.launch {
            val result = try { repository.getPhoto(id)

//            } catch (e: Exception) {
//
//            }
//            when(result){
//                is Result.Success<Photo> -> {
//
//                }
//
//
//
//            }


//          try {
//               _photo.value = repository.getPhoto(id)
//              _viewState.value = UIResponseState.Success(photo)
//
//
//          } catch (e: Exception) {
//                _viewState.value = UIResponseState.Error("Cannot load data")
//                return@launch
//            }


        }

    }


}


