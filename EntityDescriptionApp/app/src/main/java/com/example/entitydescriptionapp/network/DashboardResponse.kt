package com.example.entitydescriptionapp.network

data class DashboardResponse(
    val entities: List<Map<String, Any?>>,
    val entityTotal: Int
)
