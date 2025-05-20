package com.example.entitydescriptionapp.di

import com.example.entitydescriptionapp.network.DashboardApiService
import javax.inject.Inject

class Dash @Inject constructor(
    private val api: DashboardApiService
) {
    suspend fun getDashboardData(keypass: String) =
        api.getDashboardData(keypass)
}
