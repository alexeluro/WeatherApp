package com.inspiredcoda.weatherapp.data.response.coord


import com.google.gson.annotations.SerializedName

data class Report (
    @SerializedName("clouds")
    var clouds: Clouds?,
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
    @SerializedName("rain")
    var rain: Any?,
    @SerializedName("snow")
    var snow: Any?,
    @SerializedName("sys")
    var sys: Sys?,
    @SerializedName("weather")
    var weather: List<Weather>?,
    @SerializedName("wind")
    var wind: Wind?
)