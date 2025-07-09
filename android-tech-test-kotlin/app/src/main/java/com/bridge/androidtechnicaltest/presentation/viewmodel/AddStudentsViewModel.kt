package com.bridge.androidtechnicaltest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.androidtechnicaltest.data.helper.PupilInput
import com.bridge.androidtechnicaltest.data.helper.isPupilInputValid
import com.bridge.androidtechnicaltest.data.helper.validatePupilInput
import com.bridge.androidtechnicaltest.domain.model.Pupils
import com.bridge.androidtechnicaltest.domain.repository.PupilRepository1
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddStudentsViewModel @Inject constructor(
    private val pupilRepo: PupilRepository1,
) : ViewModel() {


    private val _InputValue = MutableStateFlow(inputValue())
    private val InputValue = _InputValue.asStateFlow()


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
        viewModelScope.launch {


            val input = PupilInput(
                country = country,
                name = name,
                latitude = latitude,
                longitude = longitude,
                pupilid = pupilId,
                image = imageUrl
            )

            val validity_check = validatePupilInput(input)

            val check = isPupilInputValid(validity_check)

            if (check) {
                println("create pupl called")

                pupilRepo.createStudents(input.toPupils()).collect { result ->
                    println("$result")
                }
            }

        }


    }


}

fun PupilInput.toPupils(): Pupils {
    return Pupils(
        country = this.country,
        name = this.name,
        latitude = this.latitude,
        longitude = this.longitude,
        pupilId = this.pupilid,
        image = this.image
    )
}
