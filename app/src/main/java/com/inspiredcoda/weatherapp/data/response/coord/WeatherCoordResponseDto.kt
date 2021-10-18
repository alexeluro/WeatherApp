package com.inspiredcoda.weatherapp.data.response.coord


import com.google.gson.annotations.SerializedName
import com.inspiredcoda.weatherapp.data.entity.CoordEntity

data class WeatherCoordResponseDto(
    @SerializedName("cod")
    var cod: String?,
    @SerializedName("count")
    var count: Int?,
    @SerializedName("list")
    var list: List<Report>?,
    @SerializedName("message")
    var message: String?
) {
    fun toCoordEntity(): CoordEntity {
        val record = list?.get(0)
        return CoordEntity(
            temp = record?.main?.temp?.toString() ?: "0",
            name = record?.name ?: "",
            weatherIconUrl = record?.weather?.get(0)?.icon ?: ""
        )
    }
}