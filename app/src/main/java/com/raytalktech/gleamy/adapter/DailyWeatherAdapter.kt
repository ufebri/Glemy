package com.raytalktech.gleamy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raytalktech.gleamy.Utils.Constants
import com.raytalktech.gleamy.databinding.ItemDailyWeatherBinding
import com.raytalktech.gleamy.model.Daily
import com.raytalktech.gleamy.model.Temp
import com.raytalktech.gleamy.model.Weather
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class DailyWeatherAdapter(private val list: List<Daily>, private val timeZone: String) :
    RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherViewHolder>() {

    class DailyWeatherViewHolder(private val binding: ItemDailyWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(daily: Daily, timeZone: String) {
            val mData = MutableLiveData<Weather>()
            for (data in daily.weather) {
                mData.value = data
            }

            val sdf = SimpleDateFormat("EEEE, d MMM yyyy", Locale.getDefault())
            val date = Date(daily.dt.toLong() * 1000)
            sdf.timeZone = TimeZone.getTimeZone(timeZone)

            val day = sdf.format(date).replaceAfter(",", "").replace(",", "")
            val dateCount = sdf.format(date).replaceBefore(",", "").replace(",", "")

            binding.tvDateDaily.text = dateCount
            binding.tvDateDay.text = day
            binding.tvDescDaily.text = mData.value?.description
            binding.tvTempDaily.text = String.format("%s°C", daily.temp.day.roundToInt())

            with(binding) {
                Glide.with(root)
                    .load(Constants.ImageBaseURL + mData.value?.icon + "@2x.png")
                    .into(ivIconWeather)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyWeatherViewHolder {
        val itemDailyWeatherBinding =
            ItemDailyWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyWeatherViewHolder(itemDailyWeatherBinding)
    }

    override fun onBindViewHolder(
        holder: DailyWeatherViewHolder,
        position: Int
    ) {
        holder.bind(list[position], timeZone)
    }

    override fun getItemCount(): Int = list.size

    fun removeAt(position: Int) {
//        var weatherEntity: WeatherEntity
//        weatherEntity.
//
//        list.removeAt(position)
        notifyItemRemoved(position)
    }
}