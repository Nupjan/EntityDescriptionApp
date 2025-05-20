package com.example.entitydescriptionapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.entitydescriptionapp.databinding.ActivityLoginBinding
import com.example.entitydescriptionapp.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private var selectedCity: String = "Sydney"
    private var locationInput: String = "sydney"  // Default location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "Login Screen Initiated", Toast.LENGTH_SHORT).show()

        setupLocationSelector()

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            viewModel.login(username, password, locationInput)
        }

        viewModel.loginResult.observe(this) { keypass ->
            keypass?.let {
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("keypass", it)
                startActivity(intent)
                finish()
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                Log.e("LOGIN_ERROR", it)
            }
        }
    }

    private fun setupLocationSelector() {
        val options = arrayOf("Sydney", "Brisbane", "Melbourne")

        val locationMap = mapOf(
            "Sydney" to "sydney",
            "Brisbane" to "ort",
            "Melbourne" to "footscray"
        )

        // Set initial button text
        binding.locationSelectButton.text = "Location: $selectedCity"

        binding.locationSelectButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Choose your location")
                .setSingleChoiceItems(options, options.indexOf(selectedCity)) { dialog, which ->
                    selectedCity = options[which]
                    locationInput = locationMap[selectedCity] ?: "sydney"
                }
                .setPositiveButton("OK") { dialog, _ ->
                    binding.locationSelectButton.text = "Location: $selectedCity"
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}
