package com.appcessible.boardthebus.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.appcessible.boardthebus.BusArrivalService
import com.appcessible.boardthebus.JsonUtil
import com.appcessible.boardthebus.TimeFormatter
import com.appcessible.boardthebus.adapters.SearchAdapter
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.databinding.FragmentSearchBinding
import com.appcessible.boardthebus.viewmodel.SearchViewModel
import com.appcessible.boardthebus.viewmodel.SearchViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    @Inject lateinit var timeFormatter: TimeFormatter
    @Inject lateinit var busArrivalService: BusArrivalService
    @Inject lateinit var database: AppDatabase
    @Inject lateinit var jsonUtil: JsonUtil

    private lateinit var adapter: SearchAdapter

    private val viewModel: SearchViewModel by activityViewModels {
        SearchViewModelFactory(busArrivalService, database, jsonUtil, requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            this.model = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        adapter = SearchAdapter(timeFormatter) { busStop ->
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    retrieveBusArrival(busStop.busStopCode)
                }
            }
        }

        binding.adapter = adapter
        binding.etSearchBus.doAfterTextChanged { search(it.toString()) }

        viewModel.getSearchResultsLiveData().observe(viewLifecycleOwner, adapter::updateResultList)
        viewModel.getBusArrivalResultsLiveData().observe(viewLifecycleOwner, adapter::updateBusArrivalList)

        return binding.root
    }

    private fun search(query: String) {
        if (query.isEmpty()) {
            adapter.updateResultList(emptyList())
            return
        }
        lifecycleScope.launchWhenResumed {
            try {
                viewModel.search(query)
            } catch (e: Exception) {
                Log.d("SearchFragment", "error loading bus stops", e)
            }
        }
    }

    private fun retrieveBusArrival(query: String) {
        lifecycleScope.launchWhenResumed {
            try {
                viewModel.searchBusArrival(query)
            } catch (e: Exception) {
                Log.d("SearchFragment", "error loading bus stops", e)
            }
        }
    }
}