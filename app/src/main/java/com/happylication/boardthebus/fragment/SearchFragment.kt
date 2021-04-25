package com.happylication.boardthebus.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.happylication.boardthebus.R
import com.happylication.boardthebus.database.AppDatabase
import com.happylication.boardthebus.databinding.FragmentFavoritesBinding
import com.happylication.boardthebus.databinding.FragmentSearchBinding
import com.happylication.boardthebus.model.mockNextBus
import com.happylication.boardthebus.viewmodel.FavoritesFragmentViewModel
import com.happylication.boardthebus.viewmodel.SearchAdapter
import com.happylication.boardthebus.viewmodel.SearchViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.*
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject lateinit var viewModel: SearchViewModel
    private var searchJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            this.model = viewModel
        }

        binding.etSearchBus.doAfterTextChanged {
            search(it.toString())
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            try {
                val busArrival = viewModel.search(query)
                viewModel.adapter.updateList(
                    busArrival.Services?.map {
                        it.NextBus ?: mockNextBus
                    } ?: emptyList()
                )
            } catch (e: Exception) {
                Log.d("SearchFragment", "error loading bus arrivals", e)
            }
        }
    }


}