package com.example.firestore.data.source.base

import androidx.lifecycle.liveData
import com.example.firestore.data.model.response.Result
import kotlinx.coroutines.Dispatchers

abstract class BaseDataSource {

    protected fun <T> getResult(task: suspend () -> T) = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {

            val result = task.invoke()
            if (result != null) {
                emit(Result.Success(result))
            } else {
                emit(Result.Error("Empty Result"))
            }

        } catch (e: Exception) {
            emit(Result.Error(e.message ?: e.toString()))
        }
    }
}