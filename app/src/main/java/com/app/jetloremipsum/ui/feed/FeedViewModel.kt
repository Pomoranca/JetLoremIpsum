package com.app.jetloremipsum.ui.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.jetloremipsum.network.Resource
import com.app.jetloremipsum.repository.MainRepository
import com.app.jetloremipsum.result.ResultItem
import kotlinx.coroutines.Dispatchers

class FeedViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private var currentEditPosition by mutableStateOf(-1)

    private var loadingItems by mutableStateOf(false)


    var resultItems by mutableStateOf(listOf<ResultItem>())
        private set

    val currentEditItem: ResultItem?
        get() = resultItems.getOrNull(currentEditPosition)

    fun addItems(results: List<ResultItem>) {
        resultItems = results
    }

    fun onEditItemSelected(item: ResultItem) {
        currentEditPosition = resultItems.indexOf(item)
    }

    fun onEditDone() {
        currentEditPosition = -1
    }

    fun getResults() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getResults()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message.toString()))
        }

    }

    fun startLoading() {
        loadingItems = true
    }

    fun endLoading(){
        loadingItems = false
    }
}