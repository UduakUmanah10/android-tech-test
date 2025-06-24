package com.bridge.androidtechnicaltest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.androidtechnicaltest.data.helper.PupilInput
import com.bridge.androidtechnicaltest.data.helper.isPupilInputValid
import com.bridge.androidtechnicaltest.data.helper.validatePupilInput
import com.bridge.androidtechnicaltest.domain.model.Pupils
import com.bridge.androidtechnicaltest.domain.repository.PupilRepository1
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateStudentViewModel @Inject constructor(
    private val pupilRepo: PupilRepository1,
) : ViewModel() {


    fun updateStudentFromInput(
        name: String, country:
        String, latitude: Double?,
        longitude: Double?,
        image: String,
        pupilId: Int?
    ) {

        val input = PupilInput(
            country = country,
            name = name,
            latitude = latitude,
            longitude = longitude,
            pupilid = pupilId,
            image = image
        )

        println(" input $input")

        val validityCheck = validatePupilInput(input)
        val isInputValid = isPupilInputValid(validityCheck)
        println("+===== $isInputValid")

        viewModelScope.launch {

            val pupil = Pupils(
                country = country,
                image = image,
                latitude = latitude,
                longitude = longitude,
                name = name,
                pupilId = pupilId
            )

            pupilRepo.updateStudents(pupil)

        }

    }

    fun deleteStudent(
        name: String,
        country: String,
        latitude: Double?,
        longitude: Double?,
        image: String,
        pupilId: Int?
    ) {

        val input = PupilInput(
            country = country,
            name = name,
            latitude = latitude,
            longitude = longitude,
            pupilid = pupilId,
            image = image
        )

        println(" input $input")

        val validityCheck = validatePupilInput(input)
        val isInputValid = isPupilInputValid(validityCheck)
        println("+===== $isInputValid")

        viewModelScope.launch {
            pupilRepo.deleteAllStudents(pupilId!!)
        }

    }
}