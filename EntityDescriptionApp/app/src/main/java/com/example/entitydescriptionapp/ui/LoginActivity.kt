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

    // Location configuration
    private val locationMap = mapOf(
        "Sydney" to "sydney",
        "Brisbane" to "ort",
        "Melbourne" to "footscray"
    )
    private var selectedCity = "Sydney"
    private val defaultLocation get() = locationMap[selectedCity] ?: "sydney"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showInitToast()
        setupLocationSelector()
        setupLoginButton()
        observeViewModel()
    }

    private fun showInitToast() {
        Toast.makeText(this, "Login Screen Initiated", Toast.LENGTH_SHORT).show()
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (validateInputs(username, password)) {
                viewModel.login(username, password, defaultLocation)
            }
        }
    }

    private fun validateInputs(username: String, password: String): Boolean {
        return when {
            username.isEmpty() -> {
                showError("Username cannot be empty")
                false
            }
            password.isEmpty() -> {
                showError("Password cannot be empty")
                false
            }
            else -> true
        }
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(this) { keypass ->
            keypass?.let { navigateToDashboard(it) }
        }

        viewModel.errorMessage.observe(this) { error ->
            error?.let { showError(it) }
        }
    }

    private fun navigateToDashboard(keypass: String) {
        Intent(this, DashboardActivity::class.java).apply {
            putExtra("keypass", keypass)
            startActivity(this)
        }
        finish()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Log.e("LOGIN_ERROR", message)
    }

    private fun setupLocationSelector() {
        val options = locationMap.keys.toTypedArray()

        binding.locationSelectButton.text = "Location: $selectedCity"

        binding.locationSelectButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Choose your location")
                .setSingleChoiceItems(options, options.indexOf(selectedCity)) { _, which ->
                    selectedCity = options[which]
                }
                .setPositiveButton("OK") { dialog, _ ->
                    updateLocationButton()
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun updateLocationButton() {
        binding.locationSelectButton.text = "Location: $selectedCity"
    }
}