package com.example.entitydescriptionapp.di


import com.example.entitydescriptionapp.network.ApiService
import com.example.entitydescriptionapp.network.AuthRequest

import javax.inject.Inject

class Auth @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun login(url: String, username: String, password: String) =
        apiService.login(url, AuthRequest(username, password))
}
