package com.bridge.androidtechnicaltest.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pupils")
class Pupil(
        @PrimaryKey
        @ColumnInfo(name = "pupil_id")
        val pupilId: Long,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "country")
        val value: String,

        @ColumnInfo(name = "image")
        val image: String,

        @ColumnInfo(name = "latitude")
        val latitude: Double,

        @ColumnInfo(name = "longitude")
        val longitude: Double

)

data class PupilList(
        val items: MutableList<Pupil> ,
        val pageNumber:Int,
        val itemCount:Int ,
        val totalPages:Int
)