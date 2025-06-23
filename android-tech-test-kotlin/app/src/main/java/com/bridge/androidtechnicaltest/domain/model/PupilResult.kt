package com.bridge.androidtechnicaltest.domain.model

sealed class PupilResult<T>(
    val data:T? = null,
    val error:String?
) {

    class Success<T>(data: T?):PupilResult<T>(data, null)
    class Error<T>(errorMessage:String):PupilResult<T>(error = errorMessage, data = null)
}