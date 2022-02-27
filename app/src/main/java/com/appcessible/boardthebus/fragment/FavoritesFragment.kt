package com.appcessible.boardthebus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.appcessible.boardthebus.R
import com.appcessible.boardthebus.database.AppDatabase
import com.appcessible.boardthebus.databinding.FragmentFavoritesBinding
import com.appcessible.boardthebus.viewmodel.FavoritesFragmentViewModel
import com.appcessible.boardthebus.viewmodel.FavoritesViewModelFactory
import com.appcessible.boardthebus.viewmodel.SearchViewModel
import com.appcessible.boardthebus.viewmodel.SearchViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FavoritesFragment : DaggerFragment() {

    @Inject lateinit var database: AppDatabase

    private val viewModel: FavoritesFragmentViewModel by activityViewModels {
        FavoritesViewModelFactory(database, requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_favorites, container, false)
        DataBindingUtil.bind<FragmentFavoritesBinding>(v)?.run {
            model = viewModel
        }
        subscribeUI()
        return v
    }

    private fun subscribeUI() {
        viewModel.busStops.observe(viewLifecycleOwner) {
            viewModel.adapter.updateList(it)
        }
    }
}