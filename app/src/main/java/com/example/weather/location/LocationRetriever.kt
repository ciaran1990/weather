package com.example.weather.location

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.weather.models.CoordinateModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationRetriever {

    @SuppressLint("MissingPermission")
    fun getLocation(
        fusedLocationClient :FusedLocationProviderClient,
        success: (CoordinateModel) -> Unit,
        failure: () -> Unit
    ) {

        val coordinates = CoordinateModel(0.0, 0.0)

        fusedLocationClient.lastLocation.addOnSuccessListener {
            coordinates.lat = it.latitude
            coordinates.lon = it.longitude
            success(coordinates)
        }
        fusedLocationClient.lastLocation.addOnFailureListener {
            failure()
        }
    }
}