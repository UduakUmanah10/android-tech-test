package com.bridge.androidtechnicaltest.data.repository

import com.bridge.androidtechnicaltest.data.local.PupilItemEntity
import com.bridge.androidtechnicaltest.data.local.PupilsDao
import com.bridge.androidtechnicaltest.data.mappers.toPupil
import com.bridge.androidtechnicaltest.data.mappers.toPupilItemDto
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


    private suspend fun getStudentsOnline(nextPageCount: Int): PupilList {
        val remotePupils = Api.getPupils()
        println(tag + " getStudentsLocally" + remotePupils.items?.size)
        return PupilList(
            itemCount = null,
            pageNumber = null,
            totalPages = null,
            items = remotePupils.items?.map { it.toPupil() }
        )
    }


    private suspend fun updateStudentsOnline(input: Pupils): PupilItemDto {
        val requestInput = input.toPupilItemDto()
        val remotePupils = Api.updatePupil(requestInput.pupilId!!, requestInput)
        return remotePupils

    }

    private suspend fun deleteStudentOnline(input: Int): Unit {
        return Api.deletePupil(input)
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
                    else -> println(tag + "updatePupil HttpException ${http.code()}: ${http.message}")
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
                return@flow
            }

            emit(PupilResult.Error("Empty database"))

        }

    }

    override suspend fun getStudentsByID(pupilId: Int): Flow<PupilResult<PupilList>> {
        return flow {
            /** Try to get the pupil remotely  first and return null if there is an error ***/

            val remotePupil = try {
                Api.getPupilsById(pupilId)
            } catch (e: Exception) {
                e.printStackTrace()
                println("$tag getPupilsById remote exception: ${e.message.toString()}")
                null
            } catch (http: HttpException) {
                val errorBody = http.response()?.errorBody()?.string()
                println("API_ERROR  Error body: $errorBody")
                http.printStackTrace()
                when (http.code()) {

                    400 -> println(tag + "get Student remote exception" + http.message)
                    401 -> println(tag + "get Student remote exception" + http.message)
                    503 -> println(tag + "get Student remote exception" + http.message)
                    404 -> println(tag + "get Student remote exception" + http.message)
                    else -> println(tag + "updatePupil HttpException ${http.code()}: ${http.message}")

                }
                null

            } catch (io: IOException) {
                io.printStackTrace()
                println(tag + "get Student remote exception" + io.message)
                null

            }

            /** if the response is non null save to room data bas which is the single source of t
            Truth
             ***/

            remotePupil?.let {
                studentsDao.upSertPupils(it.toPupilsEntity())
                val localstudent = getStudentsLocally(1)
                emit(PupilResult.Success(localstudent))
                return@flow
            }

            /** If remote is unsuccessful, try getting the data from the
             * data base locally it locally and emit **/

            val localPupil = studentsDao.getStudentById(pupilId)

            localPupil?.let {
                emit(
                    PupilResult.Success(
                        PupilList(
                            itemCount = null,
                            items = getPupilListById(localPupil),
                            pageNumber = null,
                            totalPages = null
                        )
                    )
                )
                return@flow
            }

            /** If neither remote nor local works **/

            emit(PupilResult.Error("Pupil not found with ID: $pupilId"))
        }
    }


    override suspend fun deleteAllStudents(studentID: Int): Flow<PupilResult<Pupils>> {
        return flow {
            val remotePupil = try {
                deleteStudentOnline(studentID)
            } catch (e: Exception) {
                e.printStackTrace()
                println(tag + "getPupilById exception: ${e.message}")
                null
            } catch (http: HttpException) {
                http.printStackTrace()
                when (http.code()) {
                    401 -> println(tag + "getPupilById HttpException 401: ${http.message}")
                    503 -> println(tag + "getPupilById HttpException 503: ${http.message}")
                    404 -> println(tag + "getPupilById HttpException 404: ${http.message}")
                    else -> println(tag + "getPupilById HttpException ${http.code()}: ${http.message}")
                }
                null
            } catch (io: IOException) {
                io.printStackTrace()
                println(tag + "getPupilById IOException: ${io.message}")
                null
            }

            /** If remote delete  is successful Delete from local **/
            remotePupil?.let {
                studentsDao.deleteStudentById(studentID)
                emit(PupilResult.Success(data = null))
                return@flow
            }

            /** If remote fails, check local DB  and delete **/
            val localPupil = studentsDao.deleteStudentById(studentID)

            localPupil.let {
                emit(
                    PupilResult.Success(
                        data = null
                    )
                )
                /** If not found in remote or local, emit error **/
                emit(PupilResult.Error("Pupil not found with ID"))
                return@flow
            }

        }
    }

    override suspend fun updateStudents(pupil: Pupils): Flow<PupilResult<Pupils>> {

        return flow {
            val response = try {
                updateStudentsOnline(pupil)
            } catch (e: Exception) {
                e.printStackTrace()
                println(tag + "updatePupil exception: ${e.message}")
                null
            } catch (http: HttpException) {

                http.printStackTrace()

                when (http.code()) {
                    401 -> println(tag + "updatePupil HttpException 401: ${http.message}")
                    503 -> println(tag + "updatePupil HttpException 503: ${http.message}")
                    404 -> println(tag + "updatePupil HttpException 404: ${http.message}")
                    else -> println(tag + "updatePupil HttpException ${http.code()}: ${http.message}")
                }
                null
            } catch (io: IOException) {
                io.printStackTrace()
                println(tag + "updatePupil IOException: ${io.message}")
                null
            }
            response.let {
                studentsDao.upSertPupils(pupil.toPupilsEntity().copy(
                    offlineDataOperation = 1
                ))

                emit(PupilResult.Success(data = null))
            }
            emit(PupilResult.Error("Failed to update pupil"))
        }
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

fun getPupilListById(pupilId: PupilItemEntity?): List<Pupils> {
    val list = mutableListOf<Pupils>()

    pupilId?.let {
        list.add(it.toPupil())
    }
    return list
}