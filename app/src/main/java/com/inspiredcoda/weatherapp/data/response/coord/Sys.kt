package com.inspiredcoda.weatherapp.data.response.coord


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("country")
    var country: String?
)