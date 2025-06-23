package com.bridge.androidtechnicaltest.domain.model



data class PupilList(
    val itemCount: Int?,
    val items: List<Pupils>?,
    val pageNumber: Int?,
    val totalPages: Int?
)