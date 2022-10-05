package com.example.sqlitevsroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sqlitevsroom.sqlite.StudentDatabaseHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val studentDatabase = StudentDatabaseHelper(this)
    }

}