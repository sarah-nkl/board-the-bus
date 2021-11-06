package com.appcessible.boardthebus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.appcessible.boardthebus.R
import com.appcessible.boardthebus.databinding.FragmentFavoritesBinding
import com.appcessible.boardthebus.viewmodel.FavoritesFragmentViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FavoritesFragment : DaggerFragment() {

    @Inject lateinit var viewModel: FavoritesFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_favorites, container, false)
        DataBindingUtil.bind<FragmentFavoritesBinding>(v)?.run {
            model = viewModel
        }
        subscribeUI()
        return v
    }

    private fun subscribeUI() {
        viewModel.favoriteBuses.observe(viewLifecycleOwner) {
            viewModel.adapter.updateList(it)
        }
    }
}