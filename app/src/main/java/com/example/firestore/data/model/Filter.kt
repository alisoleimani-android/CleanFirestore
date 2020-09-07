package com.example.firestore.data.model

data class Filter(
    val name: String = "",
    val fromAge: Int = -1,
    val toAge: Int = 200
)