package com.bridge.androidtechnicaltest.domain.model

data class  PupilResponse(
    val pupilId: Int,
    val country: String,
    val name: String,
    val image: String?,
    val latitude: Double,
    val longitude: Double
)