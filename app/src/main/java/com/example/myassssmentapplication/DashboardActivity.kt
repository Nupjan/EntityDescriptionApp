package com.example.myassssmentapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var apiService: ApiService
    private lateinit var keypass: String
    private lateinit var entitiesList: List<JsonObject>  // to keep entities for click

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard) // your layout with ListView

        listView = findViewById(R.id.listView) // your ListView id
        apiService = RetrofitClient.instance

        // Get keypass from intent (passed from LoginActivity)
        keypass = intent.getStringExtra("KEYPASS") ?: ""

        if (keypass.isNotEmpty()) {
            fetchDashboardData(keypass)
        } else {
            Toast.makeText(this, "No keypass provided", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchDashboardData(keypass: String) {
        apiService.getDashboardData(keypass).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful && response.body() != null) {
                    val json = response.body()!!
                    val entities = json.getAsJsonArray("entities")

                    // Save entities for use on click
                    entitiesList = entities.map { it.asJsonObject }

                    // Extract title and summary for each entity
                    val listItems = mutableListOf<String>()
                    for (entity in entitiesList) {
                        // Get first two properties for title and summary as example
                        val title = entity.entrySet().firstOrNull()?.value?.asString ?: "No Title"
                        val summary = entity.entrySet().drop(1).firstOrNull()?.value?.asString ?: "No Summary"
                        listItems.add("$title\n$summary")
                    }

                    val adapter = ArrayAdapter(this@DashboardActivity, android.R.layout.simple_list_item_1, listItems)
                    listView.adapter = adapter

                    // Set click listener to open DetailsActivity
                    listView.setOnItemClickListener { parent, view, position, id ->
                        val selectedEntity = entitiesList[position]
                        val entityJsonString = selectedEntity.toString()

                        val intent = Intent(this@DashboardActivity, DetailsActivity::class.java)
                        intent.putExtra("entity_data", entityJsonString)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this@DashboardActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(this@DashboardActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
