package com.example.myassessmentapplication
import com.google.gson.JsonObject

data class GenericResponse(
    val entities: List<JsonObject>,
    val entityTotal: Int
)
