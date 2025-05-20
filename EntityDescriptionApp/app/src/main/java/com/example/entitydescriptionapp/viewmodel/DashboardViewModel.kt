package com.example.entitydescriptionapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import com.example.entitydescriptionapp.di.Dash
import java.io.IOException
import android.util.Log
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: Dash
) : ViewModel() {

    // Encapsulated mutable LiveData
    private val _entities = MutableLiveData<List<Map<String, Any?>>>()
    val entities: LiveData<List<Map<String, Any?>>> = _entities

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadDashboard(keypass: String) {
        if (keypass.isBlank()) {
            _error.value = "Invalid keypass"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getDashboardData(keypass)
                if (response.isSuccessful) {
                    _entities.value = response.body()?.entities ?: emptyList()
                } else {
                    _error.value = when (response.code()) {
                        404 -> "Data not found"
                        401 -> "Unauthorized"
                        else -> "Error: ${response.code()}"
                    }
                }
            } catch (e: IOException) {
                _error.value = "Network error"
                Log.e("DashboardVM", "Network call failed", e)
            } catch (e: Exception) {
                _error.value = "Unexpected error"
                Log.e("DashboardVM", "API error", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}