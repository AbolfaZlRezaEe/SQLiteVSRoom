package com.example.sqlitevsroom.sqlite

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Student(
    var studentId: Long = 0,
    var firstName: String,
    var lastName: String
) : Parcelable
