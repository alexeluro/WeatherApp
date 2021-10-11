package com.inspiredcoda.weatherapp.data.response.coord


import com.google.gson.annotations.SerializedName

data class WeatherCoordResponse(
    @SerializedName("cod")
    var cod: String?,
    @SerializedName("count")
    var count: Int?,
    @SerializedName("list")
    var list: List<Report>?,
    @SerializedName("message")
    var message: String?
)