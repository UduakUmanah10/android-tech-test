package com.bridge.androidtechnicaltest.data.repository

import androidx.work.WorkManager
import com.bridge.androidtechnicaltest.data.local.PupilItemEntity
import com.bridge.androidtechnicaltest.data.local.PupilsDao
import com.bridge.androidtechnicaltest.data.mappers.toPupil
import com.bridge.androidtechnicaltest.data.mappers.toPupilsEntity
import com.bridge.androidtechnicaltest.data.remote.PupilApiService
import com.bridge.androidtechnicaltest.data.remote.model.PupilItemDto
import com.bridge.androidtechnicaltest.domain.model.PupilList
import com.bridge.androidtechnicaltest.domain.model.PupilResult
import com.bridge.androidtechnicaltest.domain.model.Pupils
import com.bridge.androidtechnicaltest.domain.repository.PupilRepository1
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PupilRepositoryImpl2 @Inject constructor(
    private val workManager: WorkManager,
    private val studentsDao: PupilsDao,
    private val Api: PupilApiService
) : PupilRepository1 {

    private val tag = "PupilRepositoryImpl2 : "

    suspend fun getStudentsLocally(nextPageCount: Int): PupilList {

        val localPupils: List<PupilItemEntity> = studentsDao.getALLStudents()

        println(tag + " getStudentsLocally" + localPupils.size)

        println("getLocalStudents count: ${localPupils.count()}")

        val mappedPupils: List<Pupils> = localPupils.map { entity ->
            Pupils(
                country = entity.country,
                image = entity.image,
                latitude = entity.latitude,
                longitude = entity.longitude,
                name = entity.name,
                pupilId = entity.pupilId,
            )
        }

        val pageSize = 20
        val paginatedItems = mappedPupils.drop((nextPageCount - 1) * pageSize).take(pageSize)
        val totalPages = (mappedPupils.size + pageSize - 1) / pageSize

        return PupilList(
            itemCount = paginatedItems.size,
            items = paginatedItems,
            pageNumber = nextPageCount,
            totalPages = totalPages
        )
    }

    suspend fun getStudentByIdLocally(studentId: Int): Pupils? {
        val entity = studentsDao.getStudentById(studentId)
        return entity?.let {
            Pupils(
                country = it.country,
                image = it.image,
                latitude = it.latitude,
                longitude = it.longitude,
                name = it.name,
                pupilId = it.pupilId
            )
        }
    }


    suspend fun getStudentsOnline(nextPageCount: Int): PupilList {
        val remotePupils = Api.getPupils()

        println(tag + " getStudentsLocally" + remotePupils.items?.size)

        return PupilList(
            itemCount = null,
            pageNumber = null,
            totalPages = null,
            items = remotePupils.items?.map { it.toPupil() }
        )
    }


    override suspend fun getPupil(): Flow<PupilResult<PupilList>> {

        return flow {
            val remoteStudent = try {

                getStudentsOnline(1).items
            } catch (e: Exception) {
                e.printStackTrace()
                println(tag + "get News Student exception" + e.message)
                null

            } catch (http: HttpException) {
                http.printStackTrace()
                when (http.code()) {

                    401 -> println(tag + "get Student remote exception" + http.message)
                    503 -> println(tag + "get Student remote exception" + http.message)
                    404 -> println(tag + "get Student remote exception" + http.message)
                    else -> {
                        println(tag + "get News remote exception" + http.code())
                    }
                }
                null

            } catch (io: IOException) {
                io.printStackTrace()
                println(tag + "get Student remote exception" + io.message)
                null

            }


            remoteStudent?.let {
                studentsDao.deleteAllStudents()
                remoteStudent.forEach {
                    studentsDao.upSertPupils(it.toPupilsEntity())
                }
                emit(PupilResult.Success(getStudentsLocally(1)))
                return@flow
            }

            val localstudent = getStudentsLocally(1)

            if (localstudent.items?.isNotEmpty()!!) {
                emit(PupilResult.Success(localstudent))
                //emit(PupilResult.Success(getStudentsOnline(1)))
                return@flow
            }

            emit(PupilResult.Error("Empty database"))

        }

    }

    override suspend fun paginate(nextPageCount: Int): Flow<PupilResult<PupilList>> {
        return flow {
            val remoteStudent = try {

                getStudentsOnline(1).items

            }
            catch (e: Exception) {
                e.printStackTrace()
                println(tag + "get News remote exception" + e.message)
                null

            } catch (http: HttpException) {
                http.printStackTrace()
                when (http.code()) {

                    401 -> println(tag + "get News remote exception" + http.message)
                    503 -> println(tag + "get News remote exception" + http.message)
                    404 -> println(tag + "get News remote exception" + http.message)
                    else -> {
                        println(tag + "get News remote exception" + http.code())
                    }
                }
                null

            } catch (io: IOException) {
                io.printStackTrace()
                println(tag + "get News remote exception" + io.message)
                null

            }


            remoteStudent?.let {
                remoteStudent.forEach {
                    studentsDao.upSertPupils(it.toPupilsEntity())
                }

                return@flow
            }


        }
    }


    override suspend fun getArticleByID(pupilId: Int): Flow<PupilResult<Pupils>> {
        return flow {

            // Try to get the pupil remotely
            val remotePupil = try {
                Api.getPupilsById(pupilId)
            } catch (e: Exception) {
                e.printStackTrace()
                println("$tag getPupilsById remote exception: ${e.message}")
                null
            }


            // If remote is successful, cache it locally and emit
            remotePupil?.let {
                studentsDao.upSertPupils(it.toPupilsEntity()) // Save to DB
                emit(PupilResult.Success(it.toPupil()))

                return@flow
            }

            // If remote fails, try to get it from local database
            val localPupil = studentsDao.getStudentById(pupilId)

            localPupil?.let {
                emit(
                    PupilResult.Success(
                        Pupils(
                            country = it.country,
                            image = it.image,
                            latitude = it.latitude,
                            longitude = it.longitude,
                            name = it.name,
                            pupilId = it.pupilId
                        )
                    )
                )
                return@flow
            }

            // If neither remote nor local works
            emit(PupilResult.Error("Pupil not found with ID: $pupilId"))
        }
    }


    override suspend fun deleteAllStudents(studentID: Int): Flow<PupilResult<Pupils>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateStudents(): Flow<PupilResult<Pupils>> {
        TODO("Not yet implemented")
    }
}


fun PupilItemDto.toPupil(): Pupils {
    return Pupils(
        country = this.country,
        image = this.image,
        latitude = this.latitude,
        longitude = this.longitude,
        name = this.name,
        pupilId = this.pupilId
    )
}