package com.inspiredcoda.weatherapp.utils

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.inspiredcoda.weatherapp.presentation.HomeFragment.Companion.LATITUDE
import com.inspiredcoda.weatherapp.presentation.HomeFragment.Companion.LONGITUDE

/**
 * A toast
 * @param [message] the message to be displayed
 * @return [Unit]
 * */
fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

/**
 * Helper method for getting your current location.
 * Call this after location permission has been granted.
 * @param [location]
 * @return [Unit]
 * */
@SuppressLint("MissingPermission")
fun Fragment.getCurrentLocation(location: (Map<String, Double>?) -> Unit) {
    val locationDetails = hashMapOf<String, Double>()
    LocationServices.getFusedLocationProviderClient(requireContext())
        .lastLocation.addOnCompleteListener(requireActivity()) {
            if (it.isSuccessful) {
                locationDetails[LONGITUDE] = it.result?.longitude!!
                locationDetails[LATITUDE] = it.result?.latitude!!
                location(locationDetails)
            }
        }
}