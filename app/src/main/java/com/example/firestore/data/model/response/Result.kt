package com.example.firestore.data.model.response

/**
 * This class is responsible for specifying the state of a request at the end.
 */
sealed class Result<T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error<T>(val message: String?) : Result<T>()

}