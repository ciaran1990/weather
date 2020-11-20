package com.example.weather.utilities

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weather.models.enums.Direction
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class Utilities {

    internal val format = "dd MMMM yyyy HH:mm:ss"

    fun formatTemperature(temp: Double): String {
        val celsius = temp - 273.15
        return "${celsius.roundToInt()}°C"
    }

    fun formatTime(time: Long): String {
        val currentDate = Date(time)
        val df: DateFormat = SimpleDateFormat(format)
        return df.format(currentDate)
    }

    fun direction(degrees: Float): String{
        return when {
            (degrees in 0.0 .. 22.5) ->  Direction.North.name
            (degrees in 22.5 .. 67.5) -> Direction.NorthEast.name
            (degrees in 67.5 .. 112.5 ) -> Direction.East.name
            (degrees in 112.5 .. 157.5 ) -> Direction.SouthEast.name
            (degrees in 157.5 .. 202.5 ) -> Direction.South.name
            (degrees in 202.5 .. 247.5 ) -> Direction.SouthWest.name
            (degrees in 247.5 .. 292.5 ) -> Direction.West.name
            (degrees in 292.5  .. 337.5 ) -> Direction.NorthWest.name
            (degrees in 337.5  .. 360.0) -> Direction.North.name
            else ->  ""
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    internal fun isNetworkConnected(connectivityManager: ConnectivityManager): Boolean {
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}