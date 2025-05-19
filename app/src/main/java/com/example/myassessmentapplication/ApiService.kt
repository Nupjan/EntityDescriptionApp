package com.example.myassessmentapplication.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @POST("sydney/auth") // change this based on your class location
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("dashboard/{keypass}")
    fun getDashboardData(@Path("keypass") keypass: String): Call<GenericResponse>


}
