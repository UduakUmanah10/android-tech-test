package com.bridge.androidtechnicaltest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pupils_database")
data class PupilItem(
    val country: String,
    val image: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    @PrimaryKey
    val pupilId: Int,
    val workType:String ="",
    val  time:Long = System.currentTimeMillis(),
    val offlineDataOperation:Int = 0,
)