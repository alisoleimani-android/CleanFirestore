package com.example.firestore.data.source.base

import android.util.Log
import androidx.lifecycle.liveData
import com.example.firestore.data.model.response.Result
import kotlinx.coroutines.Dispatchers

abstract class BaseDataSource {

    companion object {
        const val TAG = "A_S_R"
    }

    protected fun <T> getResult(task: suspend () -> T) = liveData(Dispatchers.IO) {
        emit(Result.Loading())

        try {

            val result = task.invoke()
            emit(Result.Success(result))

        } catch (e: Exception) {
            Log.i(TAG, e.message ?: e.toString())
            emit(Result.Error(e.message ?: e.toString()))
        }
    }
}