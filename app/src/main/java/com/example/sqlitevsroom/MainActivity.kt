package com.example.sqlitevsroom

import android.os.Bundle
import android.view.View
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
            changeVisibilityOfLayouts(true)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SQLiteFragment::class.java, null).commit()
        }
        binding.roomMaterialButton.setOnClickListener {
            changeVisibilityOfLayouts(true)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RoomFragment::class.java, null).commit()
        }
    }

    private fun changeVisibilityOfLayouts(showFragment: Boolean) {
        if (showFragment) {
            binding.fragmentContainer.visibility = View.VISIBLE
            binding.sqliteMaterialButton.visibility = View.GONE
            binding.roomMaterialButton.visibility = View.GONE
            binding.titleTextView.visibility = View.GONE
        } else {
            binding.fragmentContainer.visibility = View.GONE
            binding.sqliteMaterialButton.visibility = View.VISIBLE
            binding.roomMaterialButton.visibility = View.VISIBLE
            binding.titleTextView.visibility = View.VISIBLE
        }
    }
}