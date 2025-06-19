package com.bridge.androidtechnicaltest.data.repository

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.bridge.androidtechnicaltest.data.local.StudentsDao
import com.bridge.androidtechnicaltest.data.worker.StudentPeriodicWorker
import com.bridge.androidtechnicaltest.domain.model.PupilItem
import com.bridge.androidtechnicaltest.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StudentRepoImpl @Inject constructor(
    private val workManager: WorkManager,
    private val studentsDao: StudentsDao
): StudentRepository {

    override fun getQuote() {
        TODO("Not yet implemented")
    }

    override fun getAllQuotes(): Flow<List<PupilItem>> {
        return  studentsDao.getALLStudents()
    }

    override fun deletQuotes() {
        TODO("Not yet implemented")
    }

    override fun updateQuotes() {
        TODO("Not yet implemented")
    }

    override fun setupPeriodicWorkRequest() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest =
            PeriodicWorkRequest.Builder(StudentPeriodicWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()
        workManager.enqueueUniquePeriodicWork(
            "FETCH_UPDATED_STUDENT_DATA",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}