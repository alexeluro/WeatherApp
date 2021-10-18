package com.inspiredcoda.weatherapp.data

import com.inspiredcoda.weatherapp.BuildConfig
import com.inspiredcoda.weatherapp.data.response.coord.WeatherCoordResponseDto
import com.inspiredcoda.weatherapp.data.response.location.WeatherResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("data/2.5/weather")
    suspend fun getWeatherReport(
        @Query("q") location: String,
        @Query("units") unit: String = "metric",
        @Query("appid") apiKey: String = BuildConfig.API_KEY
    ): Response<WeatherResponseDto>


    @GET("data/2.5/find")
    suspend fun getWeatherReport(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("cnt") cityCount: Int = 1,
        @Query("units") unit: String = "metric",
        @Query("appid") apiKey: String = BuildConfig.API_KEY
    ): Response<WeatherCoordResponseDto>


    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
    }

}