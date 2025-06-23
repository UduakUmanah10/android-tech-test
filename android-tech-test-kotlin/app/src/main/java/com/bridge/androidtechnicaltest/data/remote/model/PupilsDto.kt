package com.bridge.androidtechnicaltest.data.remote.model

data class PupilsDto(
    val itemCount: Int?,
    val items: List<PupilItemDto>?,
    val pageNumber: Int?,
    val totalPages: Int?
)