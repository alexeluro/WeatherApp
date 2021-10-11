package com.inspiredcoda.weatherapp.data.response.location


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    var all: Int?
)