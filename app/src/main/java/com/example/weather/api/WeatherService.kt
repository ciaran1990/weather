package com.example.weather.api

import com.example.weather.models.WeatherModel
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
        @GET("/data/2.5/weather?")
        fun getLocalWeather(
            @Query("lat") lat: String,
            @Query("lon") long: String,
            @Query("appid") key: String): Call<WeatherModel>
    }
