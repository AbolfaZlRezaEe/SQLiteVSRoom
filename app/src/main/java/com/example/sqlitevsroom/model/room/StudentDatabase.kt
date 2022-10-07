package com.example.sqlitevsroom.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sqlitevsroom.model.dataclass.StudentRoomDataclass

@Database(entities = [StudentRoomDataclass::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "student_db"

        fun initDatabase(context: Context): StudentDatabase {
            return Room.databaseBuilder(
                context, StudentDatabase::class.java, DATABASE_NAME
            ).build()
        }

        fun doOperation(runnable: Runnable) {
            Thread(runnable).start()
        }
    }

    abstract fun getStudentDao(): StudentDao
}