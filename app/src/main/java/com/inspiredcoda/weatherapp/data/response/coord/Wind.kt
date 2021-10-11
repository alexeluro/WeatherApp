package com.inspiredcoda.weatherapp.data.response.coord


import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    var deg: Int?,
    @SerializedName("speed")
    var speed: Double?
)