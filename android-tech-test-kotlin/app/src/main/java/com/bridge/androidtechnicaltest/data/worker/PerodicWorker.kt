package com.bridge.androidtechnicaltest.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bridge.androidtechnicaltest.data.local.StudentsDao
import com.bridge.androidtechnicaltest.data.mappers.toPupilItem
import com.bridge.androidtechnicaltest.data.remote.PupilApiService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

const val PEROIDIC_WORK_REQUEST:String ="PERIODIC_WORK_REQUEST"
@HiltWorker
class StudentPeriodicWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val apiService: PupilApiService,
    private val studentsDao: StudentsDao
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {

            val response = apiService.getPupils()
            response.items.forEach { dto ->
                println(dto)
                studentsDao.upSert(dto.toPupilItem( PEROIDIC_WORK_REQUEST))
            }

            Result.success()
        } catch (e: Exception) {
            Log.e("StudentWorker", "Worker failed: ${e.message}")
            Result.failure()
        }
    }
}