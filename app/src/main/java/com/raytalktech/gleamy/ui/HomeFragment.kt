package com.raytalktech.gleamy.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.raytalktech.gleamy.R
import com.raytalktech.gleamy.Utils.Constants
import com.raytalktech.gleamy.Utils.SwipeToDeleteCallback
import com.raytalktech.gleamy.Utils.ViewModelFactory
import com.raytalktech.gleamy.adapter.DailyWeatherAdapter
import com.raytalktech.gleamy.databinding.FragmentHomeBinding
import com.raytalktech.gleamy.databinding.ItemCurrentWeatherBinding
import com.raytalktech.gleamy.model.Daily
import com.raytalktech.gleamy.model.DataResponse
import com.raytalktech.gleamy.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class HomeFragment : Fragment() {


    private lateinit var _binding: FragmentHomeBinding
    private lateinit var itemCurrentWeatherBinding: ItemCurrentWeatherBinding
    private val binding get() = _binding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapters: DailyWeatherAdapter
    private lateinit var latitude: String
    private lateinit var longitude: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        itemCurrentWeatherBinding = _binding.itemCurrent
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            getLastKnownLocation(requireContext())

            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

            val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
            itemTouchHelper.attachToRecyclerView(binding.rvResult)
            subscribeUi()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation(context: Context) {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers: List<String> = locationManager.getProviders(true)
        var location: Location? = null

        for (i in providers.size - 1 downTo 0) {
            location = locationManager.getLastKnownLocation(providers[i])
            if (location != null)
                break
        }


        val gps = DoubleArray(2)
        if (location != null) {
            gps[0] = location.latitude
            gps[1] = location.longitude
            latitude = gps[0].toString()
            longitude = gps[1].toString()

        }
    }

    private fun subscribeUi() {
        viewModel.getOneTimeCallWeather(latitude, longitude)
            .observe(viewLifecycleOwner, { result ->
                if (result != null) {
                    setDailyWeather(result.daily, result.timezone)
                    setCurrentWeather(result)
                } else {
                    showError("Error Fetching")
                }
            })
    }

    private fun setDailyWeather(daily: List<Daily>, timezone: String) {
        binding.rvResult.apply {
            layoutManager = LinearLayoutManager(context)
            adapters = DailyWeatherAdapter(daily, timezone)
            adapter = adapters
        }

        //configure left swipe
        swipeToDeleteCallback.leftBG =
            ContextCompat.getColor(requireActivity(), R.color.leftSwipeBG)
        swipeToDeleteCallback.leftLabel = "Favorite"
        swipeToDeleteCallback.leftIcon =
            AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_baseline_favorite_24)

        //configure right swipe
        swipeToDeleteCallback.rightBG =
            ContextCompat.getColor(requireActivity(), R.color.leftSwipeBG)
        swipeToDeleteCallback.rightLabel = "Favorite"
        swipeToDeleteCallback.rightIcon =
            AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_baseline_favorite_24)
    }


    private val swipeToDeleteCallback =
        object : SwipeToDeleteCallback(
            context,
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.addToFavorite(adapters.getSwipedData(viewHolder.absoluteAdapterPosition))

                Snackbar.make(
                    view as View,
                    getString(R.string.snackbar_msg_success),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

    private fun setCurrentWeather(data: DataResponse) {

        val sdf = SimpleDateFormat("EEE d MMM yyyy ", Locale.getDefault())
        val date = Date(data.current.dt.toLong() * 1000)
        sdf.timeZone = TimeZone.getTimeZone(data.timezone)

        val location = data.timezone.replaceBefore("/", "").replace("/", "")


        binding.itemCurrent.tvCurrentDt.text = String.format("%s, %s", location, sdf.format(date))
        binding.itemCurrent.tvCurrentTemp.text =
            String.format("%s°C", data.current.temp.roundToInt())
        binding.itemCurrent.tvCurrentDescWeather.text = data.current.weather[0].description
        binding.itemCurrent.tvValuePressure.text =
            String.format("%s hPa", data.current.pressure.toString())
        binding.itemCurrent.tvValueHumidity.text =
            String.format("%s%s", data.current.humidity.toString(), "%")
        binding.itemCurrent.tvValueWindSpeed.text =
            String.format("%s m/s", data.current.wind_speed.toString())

        Glide.with(requireActivity())
            .load(Constants.ImageRandomBaseURL + location + "/500/300")
            .into(binding.itemCurrent.ivBackCover)

        Glide.with(requireActivity())
            .load(Constants.ImageBaseURL + data.current.weather[0].icon + "@2x.png")
            .into(binding.itemCurrent.ivIconWeather)
    }

    private fun showError(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

}