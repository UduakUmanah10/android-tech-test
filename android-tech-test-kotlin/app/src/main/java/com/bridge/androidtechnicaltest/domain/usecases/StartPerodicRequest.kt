package com.bridge.androidtechnicaltest.domain.usecases

import com.bridge.androidtechnicaltest.domain.repository.PupilRepository
import javax.inject.Inject



class StartPeriodicRequest @Inject constructor(private val studentRepository: PupilRepository) {
    operator fun invoke() = studentRepository.setupPeriodicWorkRequest()
}