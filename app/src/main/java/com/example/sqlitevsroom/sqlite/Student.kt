package com.example.sqlitevsroom.sqlite

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
    var studentId: Int = 0,
    var firstName: String,
    var lastName: String
) : Parcelable
