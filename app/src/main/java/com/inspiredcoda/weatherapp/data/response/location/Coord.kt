package com.inspiredcoda.weatherapp.data.response.location


import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat")
    var lat: Double?,
    @SerializedName("lon")
    var lon: Double?
)