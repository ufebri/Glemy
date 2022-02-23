package com.raytalktech.gleamy.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raytalktech.gleamy.Utils.Constants
import com.raytalktech.gleamy.data.source.local.entity.DailyEntity
import com.raytalktech.gleamy.databinding.ItemDailyWeatherBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class FavoriteAdapter(private val list: List<DailyEntity>) :
    RecyclerView.Adapter<FavoriteAdapter.DailyWeatherViewHolder>() {

    var handler: Handler? = Handler()

    class DailyWeatherViewHolder(private val binding: ItemDailyWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(daily: DailyEntity) {


            val sdf = SimpleDateFormat("EEEE, d MMM yyyy", Locale.getDefault())
            val date = Date(daily.daily_dt.toLong() * 1000)
            sdf.timeZone = TimeZone.getTimeZone(daily.timeZone)

            val day = sdf.format(date).replaceAfter(",", "").replace(",", "")
            val dateCount = sdf.format(date).replaceBefore(",", "").replace(",", "")

            binding.tvDateDaily.text = dateCount
            binding.tvDateDay.text = day
            binding.tvDescDaily.text = daily.description_weather
            binding.tvTempDaily.text = String.format("%s°C", daily.daily_temp_day.roundToInt())

            with(binding) {
                Glide.with(root)
                    .load(Constants.ImageBaseURL + daily.icon_weather + "@2x.png")
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
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun getSwipedData(position: Int): DailyEntity {
        handler?.postDelayed({
            notifyItemChanged(position)
        }, 1000)
        return list[position]
    }
}