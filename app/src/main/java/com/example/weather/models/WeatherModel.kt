package com.example.weather.models

data class WeatherModel(var weather: List<Weather>, val main: Main, val wind: Wind, var time: Long)

data class Weather(val main: String, val description: String, val icon: String)
data class Main(val temp: Double)
data class Wind(val speed: Double, val deg: Int)

internal val defaultWeatherModel = WeatherModel(
    arrayListOf(Weather("", "", "")),
    Main(0.0),
    Wind(0.0, 0),
0L
)