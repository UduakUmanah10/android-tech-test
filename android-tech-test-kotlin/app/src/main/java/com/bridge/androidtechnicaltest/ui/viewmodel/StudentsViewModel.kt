package com.bridge.androidtechnicaltest.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.androidtechnicaltest.data.remote.ApiResult
import com.bridge.androidtechnicaltest.domain.model.PupilItem
import com.bridge.androidtechnicaltest.domain.usecases.GetAllStudentUseCase
import com.bridge.androidtechnicaltest.domain.usecases.StartPeriodicRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    private val getAllQuotesFromDbUseCase: GetAllStudentUseCase,
    private val setupPeriodicWorkRequestUseCase: StartPeriodicRequest
) : ViewModel() {

    init {
     //   setupPeriodicWorkRequestUseCase.invoke()
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
                    val data = result.responseData ?: emptyList()
                    println("===========Success: $data=========")
                    Log.d("OUTPUT", "$data")
                    UiState(data)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = UiState(emptyList())
        )


    fun refresh() {
        setupPeriodicWorkRequestUseCase.invoke()
    }
}

data class UiState(val data: List<PupilItem>)
