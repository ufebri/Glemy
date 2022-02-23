package com.raytalktech.gleamy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.raytalktech.gleamy.Utils.Constants
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
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

            subscribeUi()
        }
    }

    private fun subscribeUi() {
        viewModel.getOneTimeCallWeather("-6.200000", "106.816666")
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
            adapter = DailyWeatherAdapter(daily, timezone)
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