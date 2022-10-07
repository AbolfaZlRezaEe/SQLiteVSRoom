package com.example.sqlitevsroom.model.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentSQLiteDataclass(
    var studentId: Int = 0,
    var firstName: String,
    var lastName: String
) : Parcelable
