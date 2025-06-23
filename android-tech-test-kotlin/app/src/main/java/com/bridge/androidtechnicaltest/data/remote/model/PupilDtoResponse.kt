package com.bridge.androidtechnicaltest.data.remote.model

data class  PupilDtoResponse(
    val pupilId: Int,
    val country: String,
    val name: String,
    val image: String?,
    val latitude: Double,
    val longitude: Double
)