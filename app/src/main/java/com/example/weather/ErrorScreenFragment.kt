package com.example.weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.navigation.fragment.findNavController
import com.example.weather.utilities.Utilities
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@RequiresApi(Build.VERSION_CODES.M)
class ErrorScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_error_screen, container, false)
    }

    override fun onResume() {
        super.onResume()
        checkPermissions(requireView())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermissions(view)
    }

    private fun checkPermissions(view: View) {
        when (locationPermissionGranted()) {
            true -> {
                navigateBack(view)

                view.findViewById<TextView>(R.id.error_message).text =
                    getString(R.string.connect_to_network)

                view.findViewById<FloatingActionButton>(R.id.retry).setOnClickListener {
                    navigateBack(view)
                }
            }
            false -> {
                view.findViewById<TextView>(R.id.error_message).text =
                    getString(R.string.enable_permissions)

                view.findViewById<FloatingActionButton>(R.id.retry).setOnClickListener {
                    Snackbar.make(view, getString(R.string.no_location), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
            }
        }
    }

    private fun locationPermissionGranted() =
        (checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)

    private fun navigateBack(view: View) {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        when (Utilities().isNetworkConnected(connectivityManager)) {
            true -> findNavController().navigate(R.id.action_ErrorScreenFragment_to_WeatherFragment)
            false -> Snackbar.make(view, getString(R.string.no_network), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}