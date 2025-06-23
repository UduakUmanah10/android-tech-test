package com.bridge.androidtechnicaltest.data.repository

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.bridge.androidtechnicaltest.data.local.PupilsDao
import com.bridge.androidtechnicaltest.data.mappers.toPupilItem
import com.bridge.androidtechnicaltest.data.remote.ApiResult
import com.bridge.androidtechnicaltest.data.remote.PupilApiService
import com.bridge.androidtechnicaltest.data.worker.StudentPeriodicWorker
import com.bridge.androidtechnicaltest.data.local.PupilItemEntity
import com.bridge.androidtechnicaltest.domain.repository.PupilRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StudentRepoImpl @Inject constructor(
    private val workManager: WorkManager,
    private val studentsDao: PupilsDao,
    private val Api: PupilApiService
): PupilRepository {

    override fun getStudent() {
        TODO("Not yet implemented")
    }

    override fun getAllStudents(): Flow<ApiResult<List<PupilItemEntity>>> {
        return flow{

           val studentApi = try {
                Api.getPupils().items!!.map { it.toPupilItem("") }

            }catch (e:IOException){
                e.printStackTrace()
                val dataFromDatabase = studentsDao.getALLStudents()
               println("======database  $dataFromDatabase=======")
                emit(ApiResult.Failure(responseData = dataFromDatabase, errorMessage = "error in Io"))
                return@flow

            }
            catch (e:HttpException){
                val dataFromDatabase = studentsDao.getALLStudents()
                emit(ApiResult.Failure(responseData = dataFromDatabase, errorMessage = "error in Io"))
                return@flow
            }
            catch (e:Exception){
                e.printStackTrace()
                val dataFromDatabase = studentsDao.getALLStudents()
                emit(ApiResult.Failure(responseData = dataFromDatabase, errorMessage = "error in Io"))
                return@flow
            }

            emit(ApiResult.Success(responseData = studentApi))

        }.flowOn(Dispatchers.IO)
    }

    override fun deleteAllStudents() {
        TODO("Not yet implemented")
    }

    override fun updateStudents() {
        TODO("Not yet implemented")
    }

    override fun setupPeriodicWorkRequest() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()


        val workRequest =
            PeriodicWorkRequest
                .Builder(
                StudentPeriodicWorker::class.java,
                15,
                TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        workManager.enqueueUniquePeriodicWork(
            "FETCH_UPDATED_STUDENT_DATA",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}