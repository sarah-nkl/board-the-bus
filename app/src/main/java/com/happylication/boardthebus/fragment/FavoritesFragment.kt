package com.happylication.boardthebus.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.happylication.boardthebus.R
import com.happylication.boardthebus.database.AppDatabase
import com.happylication.boardthebus.databinding.FragmentFavoritesBinding
import com.happylication.boardthebus.viewmodel.FavoritesFragmentViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    @Inject lateinit var viewModel: FavoritesFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_favorites, container, false)
        DataBindingUtil.bind<FragmentFavoritesBinding>(v)?.run {
            model = viewModel
        }
        subscribeUI()
        return v
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun subscribeUI() {
        viewModel.favoriteBuses.observe(viewLifecycleOwner) {
            viewModel.adapter.updateList(it)
        }
    }
}