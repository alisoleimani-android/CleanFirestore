package com.example.firestore.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(
    var firstName: String = "",
    var lastName: String = "",
    var age: Int = -1
) : Parcelable