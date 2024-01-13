package com.appcessible.boardthebus.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.appcessible.boardthebus.BusArrivalService
import com.appcessible.boardthebus.R
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
    private lateinit var menuItem: MenuItem

    private val viewModel: SearchViewModel by activityViewModels {
        SearchViewModelFactory(busArrivalService, database, requireActivity())
    }

    private val resultClickListener: (SearchResult) -> Unit = { busStop ->
        binding.etSearchBus.apply {
            setText(busStop.busStopCode)
            setSelection(length())
        }
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                retrieveBusArrival(busStop.busStopCode)
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
            if (binding.etSearchBus.hasFocus()) {
                search(it.toString())
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            retrieveBusArrival(binding.etSearchBus.text.toString())
        }

        viewModel.getSearchResultsLiveData().observe(viewLifecycleOwner, adapter::updateResultList)
        viewModel.getBusArrivalResultsLiveData().observe(viewLifecycleOwner, adapter::updateBusArrivalList)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                menuItem = menu.getItem(0)
                viewModel.getFavoriteBusStopLiveData().observe(viewLifecycleOwner, ::toggleFavorite)
                viewModel.getCurrentBusStopLiveData().observe(viewLifecycleOwner, ::toggleMenuVisibility)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return if (menuItem.itemId == R.id.toggle_favorite_bus_stop) {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            updateFavorites(binding.etSearchBus.text.toString())
                        }
                    }
                    true
                } else {
                    false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun search(query: String) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                try {
                    viewModel.search(query)
                } catch (e: Exception) {
                    Log.d("SearchFragment", "error loading bus stops", e)
                } finally {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    private fun retrieveBusArrival(query: String) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                try {
                    viewModel.searchBusArrival(query)
                } catch (e: Exception) {
                    Log.d("SearchFragment", "error loading bus stops", e)
                } finally {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    private fun updateFavorites(busStopNo: String) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                try {
                    viewModel.updateFavorites(busStopNo)
                } catch (e: Exception) {
                    Log.d("SearchFragment", "error updating favorites", e)
                } finally {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    private fun toggleFavorite(isFavorite: Boolean) {
        menuItem.icon = ContextCompat.getDrawable(requireContext(),
            if (isFavorite) R.drawable.ic_star_filled_24dp else R.drawable.ic_star_outline_24)
    }

    private fun toggleMenuVisibility(busStopNo: String?) {
        menuItem.isVisible = busStopNo != null
    }
}