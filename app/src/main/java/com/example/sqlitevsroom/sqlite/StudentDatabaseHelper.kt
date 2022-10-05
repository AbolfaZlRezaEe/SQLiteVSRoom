package com.example.sqlitevsroom.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDatabaseHelper(
    context: Context
) : SQLiteOpenHelper(
    context,
    StudentQueryObject.DATABASE_NAME,
    null
    /* for using custom class of Cursor class instead of default.
    null-> use default SQLite cursor*/,
    StudentQueryObject.DATABASE_VERSION
) {


    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL(StudentQueryObject.QUERY_CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        /*
        this used only when your structure of database changed in newer
        version of application.
         */
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }
}