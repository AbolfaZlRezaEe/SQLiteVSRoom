package com.example.sqlitevsroom.model.sqlite

object StudentQueryObject {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "SampleSQLDatabase"
    const val TABLE_NAME = "tbl_student"

    // Columns
    const val COLUMN_STUDENT_ID = "student_id"
    const val COLUMN_FIRST_NAME = "first_name"
    const val COLUMN_LAST_NAME = "last_name"

    // Queries
    const val QUERY_CREATE_TABLE =
        "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_STUDENT_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_FIRST_NAME TEXT," +
                "$COLUMN_LAST_NAME TEXT)"

    const val QUERY_DELETE_TABLE = "DROP TABLE IF EXIST $TABLE_NAME"

    const val QUERY_SEARCH_WITH_FIRST_NAME = "$COLUMN_FIRST_NAME LIKE ?"
}