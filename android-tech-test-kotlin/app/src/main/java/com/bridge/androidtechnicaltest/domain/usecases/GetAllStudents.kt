package com.bridge.androidtechnicaltest.domain.usecases

import com.bridge.androidtechnicaltest.domain.repository.PupilRepository
import javax.inject.Inject

class GetAllStudents {
}

class GetAllStudentUseCase @Inject constructor(private val studentRepository: PupilRepository) {

    operator fun invoke() = studentRepository.getAllStudents()

}