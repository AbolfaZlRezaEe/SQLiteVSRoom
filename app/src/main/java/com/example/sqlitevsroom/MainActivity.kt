package com.example.sqlitevsroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sqlitevsroom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.sqliteMaterialButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SQLiteFragment::class.java, null)
        }
        binding.roomMaterialButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RoomFragment::class.java, null)
        }
    }

}