package com.bridge.androidtechnicaltest.domain.usecases

import com.bridge.androidtechnicaltest.domain.repository.StudentRepository
import javax.inject.Inject

class GetAllStudents {
}

class GetAllQuotesFromDbUseCase @Inject constructor(private val studentRepository: StudentRepository) {

    operator fun invoke() = studentRepository.getAllQuotes()

}