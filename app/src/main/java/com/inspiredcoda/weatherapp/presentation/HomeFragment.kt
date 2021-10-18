package com.inspiredcoda.weatherapp.presentation

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.inspiredcoda.weatherapp.R
import com.inspiredcoda.weatherapp.data.entity.CoordEntity
import com.inspiredcoda.weatherapp.databinding.FragmentHomeBinding
import com.inspiredcoda.weatherapp.presentation.DetailsFragment.Companion.LOCATION_QUERY
import com.inspiredcoda.weatherapp.presentation.adapter.LocationAdapter
import com.inspiredcoda.weatherapp.presentation.viewmodel.MainViewModel
import com.inspiredcoda.weatherapp.utils.ResponseState
import com.inspiredcoda.weatherapp.utils.getCurrentLocation
import com.inspiredcoda.weatherapp.utils.show
import com.inspiredcoda.weatherapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var mAdapter: LocationAdapter
    private val navController: NavController by lazy { findNavController() }
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permission ->
        if (permission) {
            getCurrentWeatherReport()
        } else {
            // No location access granted.
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)

        mAdapter = LocationAdapter {
            val bundle = bundleOf(LOCATION_QUERY to it)
            navController.navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
        }
        initRecyclerView()

        observer()

    }

    private fun observer() {
        mainViewModel.loadingState.observe(viewLifecycleOwner) { state ->
            binding.mainScreen.progressBar.show(state)
        }

        mainViewModel.weatherReportByCoord.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ResponseState.Success<*> -> {
                    val report = (response.data as CoordEntity)
                    binding.temperature.text = report.temp
                    binding.location.text = report.name
                    getWeatherIcon(report.weatherIconUrl)
                }
                is ResponseState.Failure -> {
                    toast(response.errorMessage)
                }
            }
        }
    }

    private fun getCurrentWeatherReport() {
        getCurrentLocation {
            mainViewModel.getWeatherReportByCoord(
                longitude = it?.get(LONGITUDE)!!,
                latitude = it?.get(LATITUDE)!!
            )
        }
    }

    private fun getWeatherIcon(iconId: String) {
        val url = "https://openweathermap.org/img/wn/${iconId}@2x.png"
        Glide.with(requireContext())
            .load(url)
            .into(binding.weatherIcon)
    }

    private fun initRecyclerView() {
        binding.mainScreen.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
    }

    private fun testEndpoint(location: String) {
        lifecycleScope.launch {
            mainViewModel.getWeatherReportByLocation(location)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val LONGITUDE = "longitude"
        const val LATITUDE = "latitude"
    }

}