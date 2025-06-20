package com.bridge.androidtechnicaltest.domain.repository

import com.bridge.androidtechnicaltest.data.remote.ApiResult
import com.bridge.androidtechnicaltest.domain.model.PupilItem
import kotlinx.coroutines.flow.Flow



interface StudentRepository{

    fun getStudent()

    fun getAllStudents(): Flow<ApiResult<List<PupilItem>>>

    fun deleteAllStudents()

    fun updateStudents()

    fun setupPeriodicWorkRequest()


}