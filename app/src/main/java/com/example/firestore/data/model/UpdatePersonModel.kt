package com.example.firestore.data.model

data class UpdatePersonModel(
    val person: Person,
    val changesMap: Map<String, Any>
)