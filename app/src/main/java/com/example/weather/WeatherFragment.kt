package com.example.weather

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.weather.location.LocationRetriever
import com.example.weather.models.CoordinateModel
import com.example.weather.models.WeatherModel
import com.example.weather.models.defaultWeatherModel
import com.example.weather.storage.Storage
import com.example.weather.utilities.Utilities
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.roundToInt
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WeatherFragment : Fragment() {
    private val viewModel: MainActivityViewModel by viewModels()
    private val locationRetriever = LocationRetriever()
    private val store = Storage()
    private val utilities = Utilities()

    private lateinit var sharedPref: SharedPreferences
    private lateinit var cardView: CardView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_weather, container,
            false
        )
        cardView = view.findViewById(R.id.card_view)
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateView(view)
    }

    override fun onResume() {
        super.onResume()
        locationRetriever.getLocation(fusedLocationClient, ::locationSuccess, ::locationFailure)
    }


    private fun updateView(view: View) {
        viewModel.getWeather().observe(requireActivity(), Observer<WeatherModel> {
            it.time = System.currentTimeMillis()
            store.save(sharedPref, it)
            cardView.findViewById<TextView>(R.id.time).text = getString(R.string.current_time_text)
            updateWeatherUi(it)
        })

        viewModel.getError().observe(requireActivity(), Observer<String> {
            displayStoredData()
            Snackbar.make(cardView, getString(R.string.something_went_wrong), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        })


        view.findViewById<FloatingActionButton>(R.id.refresh).setOnClickListener {
            locationRetriever.getLocation(fusedLocationClient, ::locationSuccess, ::locationFailure)
        }
    }



    @RequiresApi(Build.VERSION_CODES.M)
    private fun locationSuccess(coordinates: CoordinateModel) {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        when (utilities.isNetworkConnected(connectivityManager)) {
            true ->  viewModel.updateWeather(coordinates)
            false -> {
                Snackbar.make(cardView, getString(R.string.no_network), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                displayStoredData()
            }
        }
    }

    private fun locationFailure() {
        displayStoredData()
    }


    private fun displayStoredData() {
        val weatherModel = store.retrieve(sharedPref)
        when (weatherModel == defaultWeatherModel) {
            true -> findNavController().navigate(R.id.action_WeatherFragment_to_ErrorScreenFragment)
            false -> {
                cardView.findViewById<TextView>(R.id.time).text = getString(R.string.last_updated_text)
                updateWeatherUi(weatherModel!!)
            }
        }

    }

    private fun updateWeatherUi(weatherModel: WeatherModel) {
        cardView.findViewById<TextView>(R.id.time_value).text =
            utilities.formatTime(weatherModel.time)
        cardView.findViewById<TextView>(R.id.description_value).text =
            weatherModel.weather.first().description
        cardView.findViewById<TextView>(R.id.temperature_value).text =
            utilities.formatTemperature(weatherModel.main.temp)
        cardView.findViewById<TextView>(R.id.wind_speed_value).text =
            "${weatherModel.wind.speed.roundToInt()}mph"
        cardView.findViewById<TextView>(R.id.wind_direction_value).text =
            utilities.direction(weatherModel.wind.deg.toFloat())
        updateIcon(weatherModel.weather.first().icon)

    }

    private fun updateIcon(iconId: String) {
        val uri = "@drawable/d$iconId"
        val imageResource = resources.getIdentifier(uri, null, requireActivity().packageName)
        cardView.findViewById<ImageView>(R.id.weather_icon).setImageResource(imageResource)
    }


}