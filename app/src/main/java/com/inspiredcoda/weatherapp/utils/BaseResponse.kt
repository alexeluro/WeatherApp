package com.inspiredcoda.weatherapp.utils

sealed class BaseResponse<T>(){
    data class Success<T>(var data: T): BaseResponse<T>()
    data class Failure<T>(var error: Exception): BaseResponse<T>()
}
