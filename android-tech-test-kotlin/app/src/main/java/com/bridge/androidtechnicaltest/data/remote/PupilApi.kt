package com.bridge.androidtechnicaltest.data.remote

import com.bridge.androidtechnicaltest.data.model.PupilsDto
import com.bridge.androidtechnicaltest.db.PupilList
import com.bridge.androidtechnicaltest.domain.model.PostPupil
import com.bridge.androidtechnicaltest.domain.model.PupilResponse
import com.bridge.androidtechnicaltest.domain.model.UpdatePupilViewModel
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
   suspend fun getPupilsById(@Query("page") id: Int ):  PupilResponse


    @POST("pupils")
    suspend  fun createPupil(@Body pupil: PostPupil): PupilResponse


    @PUT("pupils/{id}")
    suspend fun updatePupil(
        @Path("id") id: Int,
        @Body updatedPupil: UpdatePupilViewModel
    ):  PupilResponse



    @DELETE("pupils/{id}")
    suspend fun deletePupil(
        @Path("id") id: Int
    )
}