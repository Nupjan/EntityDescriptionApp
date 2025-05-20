package com.example.entitydescriptionapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.entitydescriptionapp.di.Auth

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Auth
) : ViewModel() {

    val loginResult = MutableLiveData<String?>()
    val errorMessage = MutableLiveData<String?>()

    fun login(username: String, password: String, location: String) {
        val endpoint = "/$location/auth"

        viewModelScope.launch {
            try {
                val response = repository.login(endpoint, username, password)
                if (response.isSuccessful) {
                    loginResult.postValue(response.body()?.keypass)
                } else {
                    errorMessage.postValue("Login failed: ${response.code()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Error: ${e.message}")
            }
        }
    }
}