package com.bridge.androidtechnicaltest.domain.repository

import com.bridge.androidtechnicaltest.domain.model.PupilList
import com.bridge.androidtechnicaltest.domain.model.PupilResult
import com.bridge.androidtechnicaltest.domain.model.Pupils
import kotlinx.coroutines.flow.Flow

interface PupilRepository1 {

    suspend fun getPupil(): Flow<PupilResult<PupilList>>

    suspend fun paginate(nextPageCount: Int): Flow<PupilResult<PupilList>>

    suspend fun getArticleByID(pupilId: Int): Flow<PupilResult<Pupils>>

    suspend fun deleteAllStudents(studentID: Int): Flow<PupilResult<Pupils>>

    suspend fun updateStudents(pupil:Pupils): Flow<PupilResult<Pupils>>

}