package com.goble.liveweather.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class WeatherData(
    val longitude: Double,
    val latitude: Double,
    val current: CurrentWeather,
    val units: WeatherUnits,
//    val daily: DailyWeather
    val daily: List<DailyWeather> = emptyList()
)

data class DailyWeather(
    val date: String,
    val high: Double,
    val low: Double
)

@Serializable
data class OldDailyWeather(
    @SerialName("temperature_2m_max")
    val weeklyHighs: List<Double>,
    @SerialName("temperature_2m_min")
    val weeklyLows:List<Double>
)

data class CurrentWeather(
    @SerialName("temperature_2m")
    val temperature: Double,
    @SerialName("apparent_temperature")
    val feelsLike: Double,
    val weatherCode: Int,
    val precipitation: Double
)

data class WeatherUnits(
    @SerialName("temperature_2m")
    val tempUnit: String,
    val precipitationUnit: String
)