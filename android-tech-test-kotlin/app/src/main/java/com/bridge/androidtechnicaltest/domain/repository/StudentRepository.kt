package com.bridge.androidtechnicaltest.domain.repository

import com.bridge.androidtechnicaltest.domain.model.PupilItem
import com.bridge.androidtechnicaltest.domain.model.Pupils
import kotlinx.coroutines.flow.Flow



interface StudentRepository{

    fun getQuote()

    fun getAllQuotes(): Flow<List<PupilItem>>

    fun deletQuotes()

    fun updateQuotes()

    fun setupPeriodicWorkRequest()


}