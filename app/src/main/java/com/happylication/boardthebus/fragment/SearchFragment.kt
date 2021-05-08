package com.happylication.boardthebus.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.happylication.boardthebus.BusArrivalService
import com.happylication.boardthebus.JsonUtil
import com.happylication.boardthebus.TimeFormatter
import com.happylication.boardthebus.adapters.SearchAdapter
import com.happylication.boardthebus.database.AppDatabase
import com.happylication.boardthebus.database.entity.FavoriteBus
import com.happylication.boardthebus.databinding.FragmentSearchBinding
import com.happylication.boardthebus.viewmodel.SearchViewModel
import com.happylication.boardthebus.viewmodel.SearchViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    @Inject lateinit var timeFormatter: TimeFormatter
    @Inject lateinit var busArrivalService: BusArrivalService
    @Inject lateinit var database: AppDatabase
    @Inject lateinit var jsonUtil: JsonUtil

    private val viewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(busArrivalService, database, jsonUtil,  this)
    }

    private var searchJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            this.model = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        val adapter = SearchAdapter(timeFormatter, emptyList()) { busService ->
            lifecycleScope.launch {
                val favoriteBus = FavoriteBus(
                    busNo = busService.ServiceNo,
                    busStopNo = "83139"
                )
                if (busService.isFavorite) {
                    viewModel.removeFromFavorites(favoriteBus)
                } else {
                    viewModel.addToFavorites(favoriteBus)
                }
            }
        }

        binding.adapter = adapter
        binding.etSearchBus.doAfterTextChanged { search(it.toString(), adapter) }

        return binding.root
    }

    private fun search(query: String, adapter: SearchAdapter) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            try {
                val services = viewModel.search(query)
                adapter.updateList(
                    services ?: emptyList()
                )
            } catch (e: Exception) {
                Log.d("SearchFragment", "error loading bus arrivals", e)
            }
        }
    }
}