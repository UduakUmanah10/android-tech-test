package com.bridge.androidtechnicaltest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bridge.androidtechnicaltest.domain.model.PupilItem


@Database(
    entities = [PupilItem::class],
    version = 1,
    exportSchema = false
)
abstract class StudentsDatabase :RoomDatabase(){

    abstract fun getPupilsDao():StudentsDao

    companion object{


        fun getInstance(context: Context) = Room
            .databaseBuilder(
                context,
                StudentsDatabase::class.java,
                "StudentsDb"
            ).build()
    }
}