package com.inspiredcoda.weatherapp.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.inspiredcoda.weatherapp.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class InternetInterceptor(
    val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) {
            throw NoInternetException("Check your Internet connection")
        }
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        val conManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetwork = conManager.activeNetwork
//        val capability = conManager.getNetworkCapabilities(activeNetwork)
//        return (capability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false ||
//                capability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_WIFI_P2P) ?: false
//                )
        return conManager.let {
            it.activeNetworkInfo != null && it.activeNetworkInfo!!.isConnectedOrConnecting
        }
    }

}