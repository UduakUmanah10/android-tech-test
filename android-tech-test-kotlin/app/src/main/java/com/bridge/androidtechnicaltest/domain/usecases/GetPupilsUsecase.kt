package com.bridge.androidtechnicaltest.domain.usecases

import com.bridge.androidtechnicaltest.domain.repository.PupilRepository1
import javax.inject.Inject

class GetPupilsUsecase @Inject constructor(
    private val pupilRepo: PupilRepository1

) {

    suspend operator fun invoke() = pupilRepo.getPupil()
}