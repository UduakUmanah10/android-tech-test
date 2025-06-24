package com.bridge.androidtechnicaltest.data.remote

import com.bridge.androidtechnicaltest.data.remote.model.PupilsDto
import com.bridge.androidtechnicaltest.data.remote.model.PupilDtoResponse
import com.bridge.androidtechnicaltest.data.remote.model.PupilItemDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PupilApiService {
    @GET("pupils")
   suspend fun getPupils(@Query("page") page: Int = 1): PupilsDto


    @GET("pupils/{id}")
   suspend fun getPupilsById(@Path("page") id: Int ): PupilDtoResponse


    @POST("pupils")
    suspend  fun createPupil(@Body pupil:  PupilItemDto):    PupilItemDto

    @PUT("pupils/{id}")
    suspend fun updatePupil(
        @Path("id") id: Int,
        @Body updatedPupil: PupilItemDto
    ):  PupilItemDto



    @DELETE("pupils/{id}")
    suspend fun deletePupil(
        @Path("id") id: Int
    )
}