package com.bridge.androidtechnicaltest.domain.repository

import com.bridge.androidtechnicaltest.data.remote.ApiResult
import com.bridge.androidtechnicaltest.data.local.PupilItemEntity
import kotlinx.coroutines.flow.Flow



interface PupilRepository{

    fun getStudent()

    fun getAllStudents(): Flow<ApiResult<List<PupilItemEntity>>>

    fun deleteAllStudents()

    fun updateStudents()

    fun setupPeriodicWorkRequest()


}