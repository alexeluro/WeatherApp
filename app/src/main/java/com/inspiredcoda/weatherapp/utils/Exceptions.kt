package com.inspiredcoda.weatherapp.utils

import okio.IOException

class NoInternetException(message: String): IOException(message)