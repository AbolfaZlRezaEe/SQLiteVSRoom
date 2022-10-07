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
                .replace(
                    R.id.fragmentContainer,
                    SQLiteFragment(),
                    SQLiteFragment::class.java.name
                ).commit()
        }

        binding.roomMaterialButton.setOnClickListener {
            changeVisibilityOfLayouts(true)
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    RoomFragment(),
                    RoomFragment::class.java.name
                ).commit()
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

    override fun onBackPressed() {
        // Remove fragments first, if we have...
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment != null) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
            changeVisibilityOfLayouts(false)
        } else {
            super.onBackPressed()
        }
    }
}