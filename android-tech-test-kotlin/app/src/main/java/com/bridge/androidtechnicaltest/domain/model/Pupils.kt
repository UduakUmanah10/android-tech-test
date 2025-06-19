package com.bridge.androidtechnicaltest.domain.model

data class Pupils(
    val itemCount: Int,
    val items: List<PupilItem>,
    val pageNumber: Int,
    val totalPages: Int,
    val workType:String ="",
    val  time:Long = System.currentTimeMillis()
)