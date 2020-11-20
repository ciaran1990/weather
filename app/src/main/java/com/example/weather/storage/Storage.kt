package com.example.weather.storage

import android.content.SharedPreferences
import com.example.weather.models.*
import com.example.weather.models.enums.WeatherEnums

class Storage {

    private val TIMELIMIT = 86400000

    internal fun save(sharedPreferences: SharedPreferences, weatherModel: WeatherModel) {
        with(sharedPreferences.edit()) {
            putLong(WeatherEnums.time.name, weatherModel.time)
            putString(WeatherEnums.main.name, weatherModel.weather.first().main)
            putString(WeatherEnums.description.name, weatherModel.weather.first().description)
            putString(WeatherEnums.icon.name, weatherModel.weather.first().icon)
            putFloat(WeatherEnums.temp.name, weatherModel.main.temp.toFloat())
            putFloat(WeatherEnums.wind_speed.name, weatherModel.wind.speed.toFloat())
            putInt(WeatherEnums.wind_direction.name, weatherModel.wind.deg)
            apply()
        }
    }

    internal fun retrieve(sharedPreferences: SharedPreferences): WeatherModel? {
        val time = sharedPreferences.getLong(WeatherEnums.time.name, 0L)
        return when (isTimeWithinDay(time)) {
            true -> {
                val main = sharedPreferences.getString(WeatherEnums.main.name, "")!!
                val description = sharedPreferences.getString(WeatherEnums.description.name, "")!!
                val icon = sharedPreferences.getString(WeatherEnums.icon.name, "")!!
                val temp = sharedPreferences.getFloat(WeatherEnums.temp.name, 0.0F)
                val windSpeed = sharedPreferences.getFloat(WeatherEnums.wind_speed.name, 0.0F)
                val windDirection = sharedPreferences.getInt(WeatherEnums.wind_direction.name, 0)
                WeatherModel(
                    arrayListOf(Weather(main, description, icon)),
                    Main(temp.toDouble()),
                    Wind(windSpeed.toDouble(), windDirection),
                    time
                )
            }
            false -> defaultWeatherModel
        }
    }

    internal fun isTimeWithinDay(time: Long): Boolean {
        val timeDifference = System.currentTimeMillis() - time
        return timeDifference in 1 until TIMELIMIT
    }
}