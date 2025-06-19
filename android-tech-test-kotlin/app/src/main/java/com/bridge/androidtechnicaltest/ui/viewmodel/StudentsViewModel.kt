package com.bridge.androidtechnicaltest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.androidtechnicaltest.domain.model.PupilItem
import com.bridge.androidtechnicaltest.domain.usecases.GetAllQuotesFromDbUseCase
import com.bridge.androidtechnicaltest.domain.usecases.StartPeriodicRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class  StudentsViewModel  @Inject constructor(
    private val getAllQuotesFromDbUseCase: GetAllQuotesFromDbUseCase,
    private val setupPeriodicWorkRequestUseCase: StartPeriodicRequest
) : ViewModel() {

    val uiState = getAllQuotesFromDbUseCase.invoke()
        .map { UiState(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, UiState(emptyList()))

    init {
        setupPeriodicWorkRequestUseCase.invoke()
    }

    fun get(){

    }

    //fun getQuote() = getQuoteUseCase.invoke()
}

data class UiState(val data: List<PupilItem>)