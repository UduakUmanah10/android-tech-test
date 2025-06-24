package com.bridge.androidtechnicaltest.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bridge.androidtechnicaltest.data.local.PupilsDao
import com.bridge.androidtechnicaltest.data.mappers.toPupilItem
import com.bridge.androidtechnicaltest.data.remote.PupilApiService
import com.bridge.androidtechnicaltest.data.remote.model.PupilItemDto
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

const val PEROIDIC_WORK_REQUEST: String = "PERIODIC_WORK_REQUEST"

@HiltWorker
class StudentPeriodicWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val apiService: PupilApiService,
    private val studentsDao: PupilsDao
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {

            val response = apiService.getPupils()
            response.items!!.forEach { dto ->
                println(dto)
                studentsDao.upSert(dto.toPupilItem(PEROIDIC_WORK_REQUEST))
            }


            studentsDao.getALLStudents().filter { operationid ->
                operationid.offlineDataOperation == 0
            }.forEach { it ->
                apiService.deletePupil(it.pupilId)

            }


            studentsDao.getALLStudents().filter { operationId ->
                operationId.offlineDataOperation == 1

            }.forEach { pupil ->
                val update = PupilItemDto(
                    country = pupil.country,
                    image = pupil.image,
                    latitude = pupil.latitude,
                    longitude = pupil.longitude,
                    name = pupil.name,
                    pupilId = pupil.pupilId
                )
                apiService.updatePupil(id = pupil.pupilId, updatedPupil = update)
            }

            studentsDao.getALLStudents().filter { operationId ->
                operationId.offlineDataOperation == 1

            }.forEach { pupil ->
                val update = PupilItemDto(
                    country = pupil.country,
                    image = pupil.image,
                    latitude = pupil.latitude,
                    longitude = pupil.longitude,
                    name = pupil.name,
                    pupilId = pupil.pupilId
                )
                apiService.createPupil(update)
            }




            Result.success()
        } catch (e: Exception) {
            Log.e("StudentWorker", "Worker failed: ${e.message}")
            Result.failure()
        }
    }
}