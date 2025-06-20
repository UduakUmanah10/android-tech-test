package com.bridge.androidtechnicaltest.data.model

data class PupilsDto(
    val itemCount: Int,
    val items: List<PupilItemDto>,
    val pageNumber: Int,
    val totalPages: Int
)