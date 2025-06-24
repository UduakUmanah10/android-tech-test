package com.bridge.androidtechnicaltest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.androidtechnicaltest.domain.model.PupilResult
import com.bridge.androidtechnicaltest.domain.model.Pupils
import com.bridge.androidtechnicaltest.domain.repository.PupilRepository1
import com.bridge.androidtechnicaltest.domain.usecases.GetPupilsUsecase
import com.bridge.androidtechnicaltest.domain.usecases.StartPeriodicRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    private val setupPeriodicWorkRequestUseCase: StartPeriodicRequest,
    private val pupilRepo: PupilRepository1,
    private val pupilUsecase: GetPupilsUsecase
) : ViewModel() {

    init {

        viewModelScope.launch {
            pupilRepo.getPupil().collect {
                println("========= update  ${it.data?.items}")
            }
            initialValuer()
        }
    }


    private val _uiState = MutableStateFlow(newUiState())
    val uiState1 = _uiState.asStateFlow()



    fun refresh() {
        setupPeriodicWorkRequestUseCase.invoke()
    }

    fun searchForPupil(query: String) {

        viewModelScope.launch {
             pupilRepo.getStudentsByID(query.toInt()).collectLatest { data ->

                 _uiState.value = _uiState.value.copy(
                     loading = true
                 )

                 println("========= searching  ${data.data?.items}")
                when (data) {
                    is PupilResult.Error -> {
                        data.data
                        _uiState.value = _uiState.value.copy(
                            loading = false
                        )
                    }

                    is PupilResult.Success -> {
                        data.data?.items?.let {
                            _uiState.value = _uiState.value.copy(
                                data = it,
                                loading = false
                            )
                        }
                    }
                }
            }

        }
    }

    fun initialValuer() {
        viewModelScope.launch {
            pupilRepo.getPupil().collect {

            }

            pupilRepo.getPupil().collect { data ->
                println("========= new update  ${data.data?.items}")

                when (data) {
                    is PupilResult.Error -> {
                        data.data?.items?.let {
                            _uiState.value = _uiState.value.copy(
                                data = it,
                                loading = false
                            )
                        }
                    }

                    is PupilResult.Success -> {
                        data.data?.items?.let {
                            _uiState.value = _uiState.value.copy(
                                data = it,
                                loading = false
                            )
                        }
                    }
                }
            }
        }
    }
}


data class newUiState(
    val data: List<Pupils> = emptyList(),
    val loading: Boolean = true,
    val error: Boolean = false
)
