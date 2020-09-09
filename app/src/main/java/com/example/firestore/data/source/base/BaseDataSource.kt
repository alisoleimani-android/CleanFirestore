package com.example.firestore.data.source.base

import android.util.Log
import com.example.firestore.data.model.response.Result

abstract class BaseDataSource {

    companion object {
        const val TAG = "A_S_R"
    }

    protected suspend fun <T> getResult(task: suspend () -> T): Result<T> {
        return  try {
            val result = task.invoke()
            Result.Success(result)

        } catch (e: Exception) {
            Log.i(TAG, e.message ?: e.toString())
            Result.Error(e.message ?: e.toString())
        }
    }
}