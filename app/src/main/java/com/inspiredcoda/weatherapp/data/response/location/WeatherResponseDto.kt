package com.inspiredcoda.weatherapp.data.response.location


import com.google.gson.annotations.SerializedName
import com.inspiredcoda.weatherapp.data.entity.WeatherDetailEntity

data class WeatherResponseDto(
    @SerializedName("base")
    var base: String?,
    @SerializedName("clouds")
    var clouds: Clouds?,
    @SerializedName("cod")
    var cod: Int?,
    @SerializedName("coord")
    var coord: Coord?,
    @SerializedName("dt")
    var dt: Int?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("main")
    var main: Main?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("sys")
    var sys: Sys?,
    @SerializedName("timezone")
    var timezone: Int?,
    @SerializedName("visibility")
    var visibility: Int?,
    @SerializedName("weather")
    var weather: List<Weather>?,
    @SerializedName("wind")
    var wind: Wind?
) {
    fun toWeatherDetailEntity() = WeatherDetailEntity(
        name = name ?: "",
        temp = "${main?.temp ?: 0}",
        pressure = "${main?.pressure ?: 0} hPa",
        humidity = "${main?.humidity ?: 0} %",
        windSpeed = "${wind?.speed ?: 0} m/s",
        weatherIconUrl = weather?.get(0)?.icon ?: ""
    )

}