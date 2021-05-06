package com.app.jetloremipsum.utils

/**
 * A generic class that holds a value or an exception
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: String) : Result<Nothing>()
    object Loading : Result<Nothing>()

}

