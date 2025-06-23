package com.bridge.androidtechnicaltest.data.local

data class Pupils(
    val itemCount: Int?,
    val items: List<PupilItemEntity>?,
    val pageNumber: Int?,
    val totalPages: Int?,
    val workType:String? ="",
    val  time:Long = System.currentTimeMillis()
)