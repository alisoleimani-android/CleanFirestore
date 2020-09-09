package com.example.firestore.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.firestore.data.model.response.Result
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel : ViewModel() {

    private val _progress = MutableLiveData<Event<Boolean>>()
    val progress: LiveData<Event<Boolean>> = _progress

    private val _error = MutableLiveData<Event<Result.Error<*>>>()
    val error: LiveData<Event<Result.Error<*>>> = _error

    protected fun <T> watchResult(function: suspend () -> Result<T>) = liveData(Dispatchers.IO) {

        _progress.postValue(Event(true))

        val result = function.invoke()

        _progress.postValue(Event((false)))

        when (result) {
            is Result.Success -> {
                emit(Event(result.data))
            }

            is Result.Error -> {
                _error.postValue(Event(result))
            }
        }
    }

}