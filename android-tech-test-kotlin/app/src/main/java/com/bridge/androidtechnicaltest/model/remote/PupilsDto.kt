package com.bridge.androidtechnicaltest.model.remote

data class PupilsDto(
    val itemCount: Int,
    val items: List<Item>,
    val pageNumber: Int,
    val totalPages: Int
)