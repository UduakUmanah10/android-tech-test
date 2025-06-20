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
class  StudentsViewModel  @Inject constructor(
    private val getAllQuotesFromDbUseCase: GetAllStudentUseCase,
    private val setupPeriodicWorkRequestUseCase: StartPeriodicRequest
) : ViewModel() {

    private val _Students = MutableStateFlow<List<PupilItem>>(emptyList())
    private val Students = _Students.asStateFlow()

    private  val _showError = Channel<Boolean>()
    val showErrror = _showError.receiveAsFlow()


    val uiState = getAllQuotesFromDbUseCase.invoke()
        .map { it->
            when(it){
                is ApiResult.Failure<*> -> UiState(emptyList())
                is ApiResult.Success<*> -> { UiState(emptyList())
                 println(it.responseData)
                    Log.d("OUTPUT", "$it")
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, UiState(emptyList()))

    init {

        setupPeriodicWorkRequestUseCase.invoke()




    }

    fun get(){

    }

    //fun getQuote() = getQuoteUseCase.invoke()
}

data class UiState(val data: List<PupilItem>)