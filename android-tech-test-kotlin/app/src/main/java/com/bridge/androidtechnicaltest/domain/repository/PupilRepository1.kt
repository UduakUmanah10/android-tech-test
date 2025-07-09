package com.bridge.androidtechnicaltest.domain.repository

import com.bridge.androidtechnicaltest.domain.model.PupilList
import com.bridge.androidtechnicaltest.domain.model.PupilResult
import com.bridge.androidtechnicaltest.domain.model.Pupils
import kotlinx.coroutines.flow.Flow

interface PupilRepository1 {

    suspend fun getPupil(): Flow<PupilResult<PupilList>>

    suspend fun getStudentsByID(pupilId: Int): Flow<PupilResult<PupilList>>

    suspend fun deleteAllStudents(studentID: Int): Flow<PupilResult<Pupils>>

    suspend fun createStudents(studentID:Pupils): Flow<PupilResult<Pupils>>

    suspend fun updateStudents(pupil:Pupils): Flow<PupilResult<Pupils>>

}