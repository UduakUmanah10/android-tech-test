package com.bridge.androidtechnicaltest.data.remote

import com.bridge.androidtechnicaltest.data.model.PupilsDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface StudentApiService {

    @GET("pupils")
    suspend fun getPupils(
        @Query("page") page: Int,
        @Header("X-Request-ID") requestId: String = "1cf9a7bc-b2df-4e6b-940e-104c70e15c7f",
        @Header("User-Agent") userAgent: String = "Application-Name/1.0.0 (Operating System Name 1.0.0)",
        @Header("accept") accept: String = "application/json"
    ): PupilsDto
}