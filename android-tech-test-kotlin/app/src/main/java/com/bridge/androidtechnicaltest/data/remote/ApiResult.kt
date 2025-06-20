package com.bridge.androidtechnicaltest.data.remote

sealed class ApiResult<T>(
    val responseData: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(responseData: T) : ApiResult<T>(responseData = responseData)
    class Failure<T>(errorMessage: String? =null, responseData:T? = null) : ApiResult<T>(responseData = null, errorMessage = "")
}
