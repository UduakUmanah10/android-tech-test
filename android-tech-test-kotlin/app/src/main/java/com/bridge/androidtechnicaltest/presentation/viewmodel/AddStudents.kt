package com.bridge.androidtechnicaltest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.bridge.androidtechnicaltest.data.helper.PupilInput
import com.bridge.androidtechnicaltest.data.helper.isPupilInputValid
import com.bridge.androidtechnicaltest.data.helper.validatePupilInput
import com.bridge.androidtechnicaltest.domain.model.Pupils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddStudents : ViewModel() {


    private val _InputValue = MutableStateFlow(inputValue())
    private val InputValue = _InputValue.asStateFlow()


    fun enterCountry(input: String) {
        _InputValue.update { current ->
            current.copy(country = input)
        }

    }

    fun enterName(input: String) {
        _InputValue.update { current ->
            current.copy(name = input)
        }
    }

    fun enterImageUrl(input: String) {
        _InputValue.update { current ->
            current.copy(imageUrl = input)
        }
    }


    fun enterLatitude(input: Double) {
        _InputValue.update { current ->
            current.copy(latitude = input.toDouble())
        }
    }


    fun enterLongitude(input: Double) {
        _InputValue.update { current ->
            current.copy(longitude = input.toDouble())
        }
    }

    fun enterPupilId(input: Double) {
        _InputValue.update { current ->
            current.copy(pupilId = input)
        }

    }

    fun create() {
        println("++++student deleted  ${InputValue.value.name} va+++")

    }

    fun createStudent(
        country: String,
        name: String,
        latitude: Double?,
        longitude: Double?,
        pupilId: Int?, imageUrl: String
    ) {
        val input = PupilInput(
            country = country,
            name = name,
            latitude = latitude,
            longitude = longitude,
            pupilid = pupilId,
            image = imageUrl
        )

      val validity_check =  validatePupilInput(input)

        isPupilInputValid(validity_check)
        println("+===== $validity_check")


    }


}

