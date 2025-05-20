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

        setupToolbar()
        populateDetails()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Details"
        }

        // Handle back navigation (consistent with onSupportNavigateUp)
        binding.topAppBar.setNavigationOnClickListener {
            finishWithTransition()
        }
    }

    private fun populateDetails() {
        intent.extras?.keySet()?.forEach { key ->
            val value = intent.getStringExtra(key) ?: "N/A"
            addDetailTextView(key, value)
        }
    }

    private fun addDetailTextView(key: String, value: String) {
        TextView(this).apply {
            text = "$key: $value"
            textSize = 16f
            setPadding(0, 12, 0, 12)
            binding.detailsContainer.addView(this)
        }
    }

    private fun finishWithTransition() {
        finish()
        overridePendingTransition(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finishWithTransition()
        return true
    }
}