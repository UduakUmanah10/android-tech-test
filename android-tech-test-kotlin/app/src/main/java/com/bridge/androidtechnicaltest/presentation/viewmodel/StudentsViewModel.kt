package com.bridge.androidtechnicaltest.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.androidtechnicaltest.data.remote.ApiResult
import com.bridge.androidtechnicaltest.data.local.PupilItemEntity
import com.bridge.androidtechnicaltest.domain.repository.PupilRepository1
import com.bridge.androidtechnicaltest.domain.usecases.GetAllStudentUseCase
import com.bridge.androidtechnicaltest.domain.usecases.StartPeriodicRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    private val getAllQuotesFromDbUseCase: GetAllStudentUseCase,
    private val setupPeriodicWorkRequestUseCase: StartPeriodicRequest,
    private val pupilRepo: PupilRepository1
) : ViewModel() {

    init {
     //   setupPeriodicWorkRequestUseCase.invoke()
        viewModelScope.launch {
            pupilRepo.getPupil().collect{
                println("========= update  $it")


            }

        }



    }
    private val _showError = Channel<Boolean>()
    val showError = _showError.receiveAsFlow()

    val uiState = getAllQuotesFromDbUseCase.invoke()
        .map { result ->
            when (result) {
                is ApiResult.Failure -> {

                    println("====== failure ========")

                    UiState(result.responseData ?: emptyList())
                }

                is ApiResult.Success -> {
                    pupilRepo.getPupil()
                    val data = result.responseData ?: emptyList()
                    println("===========Success: $data=========")
                    pupilRepo.getPupil().collect{
                        println("========= update  $it")

                    }
                    Log.d("OUTPUT", "$data")
                    UiState(data)
                }
            }
        }.onStart {  }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = UiState(emptyList())
        )


    fun refresh() {
        setupPeriodicWorkRequestUseCase.invoke()
    }
}

data class UiState(val data: List<PupilItemEntity>)
