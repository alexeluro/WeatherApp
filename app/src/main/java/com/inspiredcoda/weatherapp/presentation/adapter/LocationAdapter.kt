package com.inspiredcoda.weatherapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inspiredcoda.weatherapp.databinding.WeatherItemBinding

class LocationAdapter(
    var onClick: ((String) -> Unit)
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val root = WeatherItemBinding.inflate(LayoutInflater.from(parent.context))
        return LocationViewHolder(root)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = LOCATIONS[position]
        holder.locationText.text = location
        holder.container.setOnClickListener {
            onClick(location)
        }
    }

    override fun getItemCount(): Int {
        return LOCATIONS.size
    }


    inner class LocationViewHolder(binding: WeatherItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val locationText = binding.location
        val container = binding.container
    }

    companion object {
        val LOCATIONS = arrayOf(
            "Kenya", "Cairo", "Lagos", "Abuja", "New York",
            "Texas", "Amazon", "Belarus", "Lesotho", "Jakarta",
            "Ankara", "Kano", "Peru", "Winnipeg", "Bagdad", "Westham"
        )
    }

}