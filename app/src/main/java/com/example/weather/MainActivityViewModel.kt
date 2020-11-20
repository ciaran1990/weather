package com.example.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.api.WeatherRetriever
import com.example.weather.models.CoordinateModel
import com.example.weather.models.WeatherModel

class MainActivityViewModel : ViewModel() {


    private var weatherData = MutableLiveData<WeatherModel>()
    private var failure = MutableLiveData<String>()
    private val repoRetriever = WeatherRetriever()


    internal fun updateWeather(coordinates: CoordinateModel) {
       repoRetriever.getWeather(
            weatherData,
            coordinates,
            failure
        )
    }

    internal fun getWeather(): LiveData<WeatherModel> {
        return weatherData
    }

    internal fun getError() : LiveData<String> {
        return failure
    }

}