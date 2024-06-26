package com.appcessible.boardthebus.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.appcessible.boardthebus.BusArrivalService
import com.appcessible.boardthebus.TimeFormatter
import com.appcessible.boardthebus.adapters.SearchAdapter
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.databinding.FragmentSearchBinding
import com.appcessible.boardthebus.model.SearchResult
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

    private lateinit var adapter: SearchAdapter
    private lateinit var binding: FragmentSearchBinding

    private val viewModel: SearchViewModel by activityViewModels {
        SearchViewModelFactory(busArrivalService, database, requireActivity())
    }

    private val resultClickListener: (SearchResult) -> Unit = { busStop ->
        binding.etSearchBus.apply {
            //setText("")
        }
        binding.swipeRefresh.isRefreshing = true
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                retrieveBusArrival(busStop.id)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            this.model = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        adapter = SearchAdapter(timeFormatter, resultClickListener)
        binding.adapter = adapter

        binding.etSearchBus.doAfterTextChanged {
            search(it.toString())
        }

        binding.swipeRefresh.setOnRefreshListener {
            retrieveBusArrival(binding.etSearchBus.text.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.checkIsFavorite()
            }
        }

        viewModel.getSearchResultsLiveData().observe(viewLifecycleOwner, adapter::updateResultList)
        viewModel.getBusArrivalResultsLiveData().observe(viewLifecycleOwner, adapter::updateBusArrivalList)

        return binding.root
    }

    private fun search(query: String) {
        lifecycleScope.launch {
            try {
                viewModel.search(query)
            } catch (e: Exception) {
                Log.d("SearchFragment", "error loading bus stops", e)
            } finally {
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun retrieveBusArrival(query: String) {
        lifecycleScope.launch {
            try {
                viewModel.searchBusArrival(query)
            } catch (e: Exception) {
                Log.d("SearchFragment", "error retrieving bus arrival", e)
            } finally {
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }
}