package com.bridge.androidtechnicaltest.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditStudent:ViewModel() {


    private val _InputValue = MutableStateFlow(inputValue())
    private val InputValue = _InputValue.asStateFlow()



    fun enterCountry(input:String){
        _InputValue.update { current ->
            current.copy(country = input)
        }

    }

    fun enterName(input:String){
        _InputValue.update { current ->
            current.copy(name = input)
        }
    }

    fun enterImage(input:String){
        _InputValue.update { current ->
            current.copy(name = input)
        }
    }

    fun enterLatitude(input:Double){
        _InputValue.update { current ->
            current.copy( latitude= input.toDouble())
        }
    }


    fun enterLongitude(input:Double){
        _InputValue.update { current ->
            current.copy( longitude = input.toDouble())
        }
    }




    fun  deleteStudent(){

    }

    fun updateStudent(){


    }
}