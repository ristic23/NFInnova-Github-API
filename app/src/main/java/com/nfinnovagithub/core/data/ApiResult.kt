package com.nfinnovagithub.core.data

sealed class ApiResult<out T> {
    data class Loading<out T>(val data: T? = null) : ApiResult<T>()
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class EmptyResult<out T>(val data: T? = null) : ApiResult<T>()
    data class Error<out T>(val error: Throwable) : ApiResult<T>()
}