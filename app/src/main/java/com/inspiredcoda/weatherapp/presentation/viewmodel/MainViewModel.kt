package com.inspiredcoda.weatherapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inspiredcoda.weatherapp.data.WeatherRepository
import com.inspiredcoda.weatherapp.data.entity.CoordEntity
import com.inspiredcoda.weatherapp.data.entity.WeatherDetailEntity
import com.inspiredcoda.weatherapp.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    var repository: WeatherRepository
) : ViewModel() {

    private var _weatherReportByCoord = MutableLiveData<ResponseState>()
    val weatherReportByCoord: LiveData<ResponseState>
        get() = _weatherReportByCoord

    private var _weatherReportByLocation = MutableLiveData<ResponseState>()
    val weatherReportByLocation: LiveData<ResponseState>
        get() = _weatherReportByLocation

    private var _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    fun getWeatherReportByCoord(longitude: Double, latitude: Double) {
        viewModelScope.launch {
            _loadingState.postValue(true)
            when (val response = repository.getWeatherReport(longitude, latitude)) {
                is ResponseState.Success<*> -> {
                    _loadingState.postValue(false)
                    _weatherReportByCoord.postValue(ResponseState.Success(response.data as CoordEntity))
                }
                is ResponseState.Failure -> {
                    _loadingState.postValue(false)
                    _weatherReportByCoord.postValue(ResponseState.Failure(response.errorMessage))
                }
            }
        }
    }

    fun getWeatherReportByLocation(location: String) {
        viewModelScope.launch {
            _loadingState.postValue(true)
            when (val response = repository.getWeatherReport(location)) {
                is ResponseState.Success<*> -> {
                    _loadingState.postValue(false)
                    _weatherReportByLocation.postValue(ResponseState.Success(response.data as WeatherDetailEntity))
                }
                is ResponseState.Failure -> {
                    _loadingState.postValue(false)
                    _weatherReportByLocation.postValue(ResponseState.Failure(response.errorMessage))
                }
            }
        }
    }

}