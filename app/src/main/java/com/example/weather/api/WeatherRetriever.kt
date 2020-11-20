package com.example.weather.api

// Other imported classes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather.models.CoordinateModel
import com.example.weather.models.WeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRetriever {
    private val service: WeatherService

    //If I had more time I would have encrypted the API key or created a vault system to store key
    companion object {
        const val BASE_URL = "https://api.openweathermap.org"
        const val API_KEY = "9f329909a46082975f29198f8ec91615"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(WeatherService::class.java)
    }

    fun getWeather(
        data: MutableLiveData<WeatherModel>,
        coordinates: CoordinateModel,
        failureMessage: MutableLiveData<String>
    ): LiveData<WeatherModel> {
        service.getLocalWeather(coordinates.lat.toString(), coordinates.lon.toString(), API_KEY)
            .enqueue(
                object : Callback<WeatherModel> {
                    override fun onResponse(
                        call: Call<WeatherModel>?,
                        response: Response<WeatherModel>?
                    ) {
                        when (response?.isSuccessful) {
                            true -> data.value = response?.body()
                            else -> failureMessage.value = response?.errorBody().toString()
                        }
                    }

                    override fun onFailure(call: Call<WeatherModel>?, t: Throwable?) {
                        failureMessage.value = t?.localizedMessage
                    }
                }
            )

        return data
    }

}
