//package com.bridge.androidtechnicaltest.data.worker
//
//import android.content.Context
//import androidx.hilt.work.HiltWorker
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import com.bridge.androidtechnicaltest.data.local.PupilsDao
//import com.bridge.androidtechnicaltest.data.mappers.toPupilItem
//import com.bridge.androidtechnicaltest.data.remote.PupilApiService
//import dagger.assisted.Assisted
//import dagger.assisted.AssistedInject
//
//@HiltWorker
//class FetchWorker @AssistedInject constructor(
//    @Assisted private val context: Context,
//    @Assisted private val workerParameters: WorkerParameters,
//    private val apiService: PupilApiService,
//    private val studentsDao: PupilsDao
//) : CoroutineWorker(context, workerParameters) {
//
//    override suspend fun doWork(): Result {
//        return try {
//
//            val response = apiService.getPupils()
//            response.items!!.forEach { dto ->
//                studentsDao.upSert(dto.toPupilItem(""))
//            }
//
//            Result.success()
//        } catch (e: Exception) {
//            Result.failure()
//        }
//    }
//}