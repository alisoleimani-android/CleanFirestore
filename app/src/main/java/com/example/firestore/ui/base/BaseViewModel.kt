package com.example.firestore.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.firestore.data.model.response.Result
import kotlinx.coroutines.Dispatchers

/**
 * A base class for all ViewModel in app.
 *
 * @author Ali Soleimani
 * @since 10th September 2020
 */
abstract class BaseViewModel : ViewModel() {

    private val _progress = MutableLiveData<Event<Boolean>>()
    val progress: LiveData<Event<Boolean>> = _progress

    private val _error = MutableLiveData<Event<Result.Error<*>>>()
    val error: LiveData<Event<Result.Error<*>>> = _error

    /**
     * Watches the result of a request. There are three types of result
     * in [Result] sealed class. This function just emit successful state
     * with data.
     *
     * @param function, which is a suspend function and returns [Result] of the request
     * @return a [LiveData] which emits successful state with data
     */
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