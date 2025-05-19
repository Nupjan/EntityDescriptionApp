package com.example.myassessmentapplication

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonParser
import android.view.View

class DetailsActivity : AppCompatActivity() {

    private lateinit var detailsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Initialize the detailsContainer here
        detailsContainer = findViewById(R.id.detailsContainer)
        detailsContainer.visibility = View.VISIBLE

        val entityJson = intent.getStringExtra("ENTITY_JSON")

        if (entityJson != null) {
            displayEntityDetails(entityJson)
        } else {
            val errorText = TextView(this)
            errorText.text = "No details available"  // minimal fix, no resource string
            detailsContainer.addView(errorText)
        }
    }

    private fun displayEntityDetails(entityJson: String) {
        val jsonObject = JsonParser.parseString(entityJson).asJsonObject

        for ((key, value) in jsonObject.entrySet()) {
            val textView = TextView(this)
            textView.text = "$key: ${value.asString}"  // minimal fix, no resource string
            textView.textSize = 16f
            textView.setPadding(0, 8, 0, 8)

            detailsContainer.addView(textView)
        }
    }
}
