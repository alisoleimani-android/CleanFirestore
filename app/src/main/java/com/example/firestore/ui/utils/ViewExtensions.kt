package com.example.firestore.ui.utils

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun SwipeRefreshLayout.show() {
    isRefreshing = true
}

fun SwipeRefreshLayout.hide() {
    isRefreshing = false
}