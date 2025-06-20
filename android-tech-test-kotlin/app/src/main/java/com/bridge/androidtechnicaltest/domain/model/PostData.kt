package com.bridge.androidtechnicaltest.domain.model


data class PostPupil(
    val country: String,
    val name: String,
    val image: String? = null,
    val latitude: Double,
    val longitude: Double
)

data class UpdatePupilViewModel(
    val pupilId: Int,
    val country: String,
    val name: String,
    val image: String? = null,
    val latitude: Double,
    val longitude: Double
)