package com.example.sqlitevsroom.model.dataclass

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "tbl_student"
)
@Parcelize
data class StudentRoomDataclass(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var studentId: Int = 0,
    @ColumnInfo(name = "first_name")
    var firstName: String,
    @ColumnInfo(name = "last_name")
    var lastName: String
) : Parcelable
