package com.bridge.androidtechnicaltest.data.model

data class PupilsDto(
    val itemCount: Int,
    val items: List<ItemDto>,
    val pageNumber: Int,
    val totalPages: Int
)