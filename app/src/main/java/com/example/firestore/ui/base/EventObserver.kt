package com.example.firestore.ui.base

import androidx.lifecycle.Observer

/**
 * An [Observer] for [Event]referenceType, simplifying the pattern of checking if the [Event]'referenceType content has
 * already been consumed.
 *
 * [onEventUnconsumedContent] is *only* called if the [Event]'referenceType contents has not been consumed.
 */
class EventObserver<T>(private val onEventUnconsumedContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.consume()?.run(onEventUnconsumedContent)
    }
}
