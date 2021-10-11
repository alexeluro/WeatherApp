package com.inspiredcoda.weatherapp.utils

import android.view.View
import android.widget.ProgressBar


/**
 * A helper method for toggling the visibility of a [ProgressBar]
 * @param [show] the toggle state as a [Boolean]
 * @return [Unit]
 * */
fun ProgressBar.show(show: Boolean) {
    visibility = if (show) {
        View.VISIBLE
    } else {
        View.GONE
    }
}