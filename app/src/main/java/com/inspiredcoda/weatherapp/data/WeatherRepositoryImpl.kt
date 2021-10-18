package com.inspiredcoda.weatherapp.data

import android.content.ContentValues.TAG
import android.util.Log
import com.inspiredcoda.weatherapp.data.response.coord.WeatherCoordResponseDto
import com.inspiredcoda.weatherapp.data.response.location.WeatherResponseDto
import com.inspiredcoda.weatherapp.utils.BaseResponse
import com.inspiredcoda.weatherapp.utils.NoInternetException
import com.inspiredcoda.weatherapp.utils.ResponseState
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    var api: Api
) : WeatherRepository {


    override suspend fun getWeatherReport(
        longitude: Double,
        latitude: Double
    ): ResponseState {
        return try {
            when (val response = apiRequest { api.getWeatherReport(latitude, longitude) }) {
                is BaseResponse.Success -> {
                    ResponseState.Success((response.data as WeatherCoordResponseDto).toCoordEntity())
                }
                is BaseResponse.Failure -> {
                    ResponseState.Failure(response.error.message.toString())
                }
                else -> {
                    ResponseState.Failure("An error has occurred")
                }
            }
        } catch (ex: NoInternetException) {
            ResponseState.Failure(ex.message.toString())
        }
    }

    override suspend fun getWeatherReport(location: String): ResponseState {
        return try {
            when (val response = apiRequest { api.getWeatherReport(location) }) {
                is BaseResponse.Success -> {
                    ResponseState.Success((response.data as WeatherResponseDto).toWeatherDetailEntity())
                }
                is BaseResponse.Failure -> {
                    ResponseState.Failure(response.error.message.toString())
                }
            }
        } catch (ex: NoInternetException) {
            ResponseState.Failure(ex.message.toString())
        }
    }


    private suspend fun <T> apiRequest(request: suspend (() -> Response<T>)): BaseResponse<T> {
        val response = request.invoke()
        return if (response.isSuccessful) {
            BaseResponse.Success(response.body()!!)
        } else {
            Log.d(TAG, "apiRequest: ${response.errorBody()?.string()}")
            BaseResponse.Failure(IOException("Something went wrong"))
        }
    }


}