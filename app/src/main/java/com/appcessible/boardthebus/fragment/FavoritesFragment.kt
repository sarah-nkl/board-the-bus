package com.appcessible.boardthebus.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.appcessible.boardthebus.BusArrivalService
import com.appcessible.boardthebus.adapters.FavoritesAdapter
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.database.entity.BusStop
import com.appcessible.boardthebus.databinding.FragmentFavoritesBinding
import com.appcessible.boardthebus.model.BusStops
import com.appcessible.boardthebus.viewmodel.FavoritesViewModel
import com.appcessible.boardthebus.viewmodel.FavoritesViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoritesFragment : DaggerFragment() {

    @Inject lateinit var busArrivalService: BusArrivalService
    @Inject lateinit var database: AppDatabase

    private lateinit var adapter: FavoritesAdapter
    private lateinit var binding: FragmentFavoritesBinding

    private val viewModel: FavoritesViewModel by activityViewModels {
        FavoritesViewModelFactory(busArrivalService, database, requireActivity())
    }

    private val emptyObserver: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            binding.tvEmptyView.isVisible = adapter.itemCount == 0
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false).apply {
            model = viewModel
        }

        adapter = FavoritesAdapter(::showBusArrivalDialog) { busStopNo ->
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    removeFromFavorites(busStopNo)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                try {
                    viewModel.retrieveFavorites()
                } catch (e: Exception) {
                    Log.d("FavoritesFragment", "error retrieving favorites", e)
                }
            }
        }

        binding.adapter = adapter
        subscribeUI()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        adapter.registerAdapterDataObserver(emptyObserver)
    }

    override fun onPause() {
        super.onPause()
        adapter.unregisterAdapterDataObserver(emptyObserver)
    }

    private fun showBusArrivalDialog(busStop: BusStop) {
        lifecycleScope.launch {
            binding.progressIndicator.isVisible = true
            try {
                val busArrivalList = viewModel.searchBusArrival(busStop.busStopNo) as ArrayList
                val busStopParcelable = BusStops(
                    busStop.busStopNo,
                    busStop.roadName,
                    busStop.description,
                    busStop.latitude,
                    busStop.longitude)

                val busArrivalDialogFragment = BusArrivalDialogFragment()
                val bundle = Bundle().apply {
                    putParcelable(BusArrivalDialogFragment.BUS_STOP_KEY, busStopParcelable)
                    putParcelableArrayList(BusArrivalDialogFragment.BUS_ARRIVAL_LIST_KEY, busArrivalList)
                }
                busArrivalDialogFragment.arguments = bundle
                busArrivalDialogFragment.show(childFragmentManager, BusArrivalDialogFragment.TAG)
            } catch (e: Exception) {
                Log.d("FavoritesFragment", "error retrieving bus arrival", e)
            } finally {
                binding.progressIndicator.isVisible = false
            }
        }
    }

    private fun subscribeUI() {
        viewModel.getBusStopsLiveData().observe(viewLifecycleOwner, adapter::updateList)
    }

    private fun removeFromFavorites(busStopNo: String) {
        lifecycleScope.launch {
            try {
                viewModel.removeFromFavorites(busStopNo)
            } catch (e: Exception) {
                Log.d("FavoritesFragment", "error removing from favorites", e)
            }
        }
    }
}