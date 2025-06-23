package com.bridge.androidtechnicaltest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface PupilsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upSert(student: PupilItemEntity)

    // CORRECTED: Added 'suspend' keyword
    @Query("SELECT * FROM pupils_database ORDER BY time DESC")
    suspend fun getALLStudents(): List<PupilItemEntity>

    // CORRECTED: Added 'suspend' keyword
    @Query("SELECT * FROM pupils_database WHERE pupilId = :studentId LIMIT 1")
    suspend fun getStudentById(studentId: Int): PupilItemEntity?

    @Upsert
    suspend fun upSertPupils(student: PupilItemEntity)

    @Query("DELETE FROM pupils_database")
    suspend fun deleteAllStudents()

    @Query("DELETE FROM pupils_database WHERE pupilId = :studentId")
    suspend fun deleteStudentById(studentId: Int)

}