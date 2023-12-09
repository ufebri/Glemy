package com.raytalktech.gleamy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.raytalktech.gleamy.R
import com.raytalktech.gleamy.utils.SwipeToDeleteCallback
import com.raytalktech.gleamy.utils.ViewModelFactory
import com.raytalktech.gleamy.adapter.FavoriteAdapter
import com.raytalktech.gleamy.data.source.local.entity.DailyEntity
import com.raytalktech.gleamy.databinding.FragmentFavoriteBinding
import com.raytalktech.gleamy.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private lateinit var _binding: FragmentFavoriteBinding
    private val binding get() = _binding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapters: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

            val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
            itemTouchHelper.attachToRecyclerView(binding.rvResult)
            subscribeUi()
        }
    }

    private fun subscribeUi() {
        viewModel.getAllFavorite().observe(viewLifecycleOwner, { result ->
            if (result != null) setFavoriteWeather(result)
        })
    }

    private fun setFavoriteWeather(result: List<DailyEntity>) {
        binding.rvResult.apply {
            layoutManager = LinearLayoutManager(context)
            adapters = FavoriteAdapter(result)
            adapter = adapters
        }

        //configure left swipe
        swipeToDeleteCallback.leftBG =
            ContextCompat.getColor(requireActivity(), R.color.rightSwipeBG)
        swipeToDeleteCallback.leftLabel = "UnFavorite"
        swipeToDeleteCallback.leftIcon =
            AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_baseline_favorite_24)

        //configure right swipe
        swipeToDeleteCallback.rightBG =
            ContextCompat.getColor(requireActivity(), R.color.rightSwipeBG)
        swipeToDeleteCallback.rightLabel = "UnFavorite"
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
                viewModel.deleteFavorite(adapters.getSwipedData(viewHolder.absoluteAdapterPosition))

                Snackbar.make(
                    view as View,
                    getString(R.string.snackbar_msg_success),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
}