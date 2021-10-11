package com.inspiredcoda.weatherapp.data

import com.inspiredcoda.weatherapp.utils.ResponseState

interface WeatherRepository {

    suspend fun getWeatherReport(longitude: Double, latitude: Double): ResponseState

    suspend fun getWeatherReport(location: String): ResponseState

}