package com.example.entitydescriptionapp.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.entitydescriptionapp.databinding.ActivityDetailsBinding



class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Details"

        binding.topAppBar.setNavigationOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        val container = binding.detailsContainer
        intent.extras?.keySet()?.forEach { key ->
            val value = intent.getStringExtra(key) ?: "N/A"
            val textView = TextView(this).apply {
                text = "$key: $value"
                textSize = 16f
                setPadding(0, 12, 0, 12)
            }
            container.addView(textView)
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
