package com.appcessible.boardthebus.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.appcessible.boardthebus.BusArrivalService
import com.appcessible.boardthebus.JsonUtil
import com.appcessible.boardthebus.TimeFormatter
import com.appcessible.boardthebus.adapters.BusStopAdapter
import com.appcessible.boardthebus.adapters.SearchAdapter
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.database.entity.Bus
import com.appcessible.boardthebus.database.entity.BusStop
import com.appcessible.boardthebus.databinding.FragmentSearchBinding
import com.appcessible.boardthebus.viewmodel.SearchViewModel
import com.appcessible.boardthebus.viewmodel.SearchViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    @Inject lateinit var timeFormatter: TimeFormatter
    @Inject lateinit var busArrivalService: BusArrivalService
    @Inject lateinit var database: AppDatabase
    @Inject lateinit var jsonUtil: JsonUtil

    private val viewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(busArrivalService, database, jsonUtil,  this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            this.model = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        val adapter = BusStopAdapter(emptyList()) { busStop ->
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    if (busStop.isFavorite) {
                        viewModel.removeFromFavorites(busStop)
                    } else {
                        viewModel.addToFavorites(busStop)
                    }
                }
            }
        }

        binding.adapter = adapter
        binding.etSearchBus.doAfterTextChanged { search(it.toString()) }

        viewModel.getBusStopsLiveData().observe(this, adapter::updateList)

        return binding.root
    }

    private fun search(query: String) {
        lifecycleScope.launchWhenResumed {
            try {
                viewModel.search(query)
            } catch (e: Exception) {
                Log.d("SearchFragment", "error loading bus stops", e)
            }
        }
    }
}