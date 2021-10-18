package com.inspiredcoda.weatherapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.inspiredcoda.weatherapp.data.entity.WeatherDetailEntity
import com.inspiredcoda.weatherapp.databinding.FragmentDetailsBinding
import com.inspiredcoda.weatherapp.presentation.viewmodel.MainViewModel
import com.inspiredcoda.weatherapp.utils.ResponseState
import com.inspiredcoda.weatherapp.utils.show
import com.inspiredcoda.weatherapp.utils.toast
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navBack.setOnClickListener {
            navController.popBackStack()
        }

        val location = arguments?.getString(LOCATION_QUERY)
        if (location != null) getWeatherReport(location)
        observer()
    }

    private fun getWeatherReport(location: String) {
        lifecycleScope.launch {
            mainViewModel.getWeatherReportByLocation(location)
        }
    }

    private fun getWeatherIcon(iconId: String) {
        val url = "https://openweathermap.org/img/wn/${iconId}@2x.png"
        Glide.with(requireContext())
            .load(url)
            .into(binding.weatherIcon)
    }

    private fun observer() {
        mainViewModel.loadingState.observe(viewLifecycleOwner) { state ->
            binding.progressBar.show(state)
        }

        mainViewModel.weatherReportByLocation.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ResponseState.Success<*> -> {
                    val report = (response.data as WeatherDetailEntity)

                    binding.temp.text = report.temp
                    binding.location.text = report.name
                    binding.pressure.text = report.pressure
                    binding.humidity.text = report.humidity
                    binding.wind.text = report.windSpeed
                    getWeatherIcon(report.weatherIconUrl)
                }
                is ResponseState.Failure -> {
                    navController.popBackStack()
                    toast(response.errorMessage)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val LOCATION_QUERY = "location"
    }

}