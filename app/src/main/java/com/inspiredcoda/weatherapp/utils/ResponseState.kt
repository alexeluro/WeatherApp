package com.inspiredcoda.weatherapp.utils

sealed class ResponseState() {
    data class Failure(var errorMessage: String) : ResponseState()
    data class Success<T>(var data: T) : ResponseState()
}
