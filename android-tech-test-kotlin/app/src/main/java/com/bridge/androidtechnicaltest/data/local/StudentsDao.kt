package com.bridge.androidtechnicaltest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bridge.androidtechnicaltest.domain.model.PupilItem
import kotlinx.coroutines.flow.Flow


@Dao
interface StudentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upSert(student:PupilItem)

    @Query("SELECT * FROM pupils_database ORDER BY time DESC")
    fun getALLStudents(): Flow<List<PupilItem>>

    @Query("SELECT * FROM pupils_database WHERE pupilId = :studentId LIMIT 1")
    fun getStudentById(studentId: Int): Flow<PupilItem?>

}