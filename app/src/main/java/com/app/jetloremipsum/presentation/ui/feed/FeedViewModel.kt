package com.app.jetloremipsum.presentation.ui.feed

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.jetloremipsum.presentation.util.TAG
import com.app.jetloremipsum.repository.PostsRepository
import com.app.jetloremipsum.result.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class FeedViewModel @Inject constructor(
    val repository: PostsRepository,
    ) : ViewModel() {

    val photos: MutableState<List<Photo>> = mutableStateOf(ArrayList())

    val loading = mutableStateOf(false)

    val page = mutableStateOf(1)

    var photoListScrollPosition = 0

    init {
            onTriggerEvent(PhotoListEvent.RestoreStateEvent)
    }


    private fun onTriggerEvent(event : PhotoListEvent){
        viewModelScope.launch {

            try {
                when(event){
                    is PhotoListEvent.NewSearchEvent -> {
                    }
                    is PhotoListEvent.NextPageEvent -> {
                        nextPage()
                    }
                    is PhotoListEvent.RestoreStateEvent -> {
                        restoreState()
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
            finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    private suspend fun restoreState(){
        loading.value = true

        // randomly delay API to show loading animation
        delay(Random.nextLong(0, 1000))

        val results: MutableList<Photo> = mutableListOf()
        for(p in 1..page.value){
            val result = repository.getPhotos(p)
            results.addAll(result)
            if(p == page.value){ // done
                photos.value = results
                loading.value = false
            }
        }
    }

    private suspend fun nextPage(){

        // prevent duplicate event due to recompose happening to quickly
        if((photoListScrollPosition + 1) >= (page.value * PAGE_SIZE) ){
            loading.value = true
            incrementPage()
            Log.d(TAG, "nextPage: triggered: ${page.value}")

            // just to show pagination, api is fast
            delay(1000)

            if(page.value > 1){
                val result = repository.getPhotos(page.value)
                Log.d(TAG, "search: appending")
                appendRecipes(result)
            }
            loading.value = false
        }
    }

    private fun incrementPage(){
        setPage(page.value + 1)
    }

    private fun setPage(page: Int){
        this.page.value = page

    }

    private fun appendRecipes(recipes: List<Photo>){
        val current = ArrayList(this.photos.value)
        current.addAll(recipes)
        this.photos.value = current
    }
}

sealed class PhotoListEvent {
    object NewSearchEvent : PhotoListEvent()

    object NextPageEvent : PhotoListEvent()

    // restore after process death
    object RestoreStateEvent: PhotoListEvent()
}

