package com.happylication.boardthebus.viewmodel

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.happylication.boardthebus.BusArrivalService
import com.happylication.boardthebus.R
import com.happylication.boardthebus.database.AppDatabase
import com.happylication.boardthebus.model.NextBus
import com.happylication.boardthebus.model.mockNextBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class FavoritesFragmentViewModel @Inject constructor(
    database: AppDatabase
) {

    val adapter = FavoritesAdapter(listOf(mockNextBus))
}

class FavoritesAdapter(
    val busList: List<NextBus>
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    class FavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvBusNumber = view.findViewById<TextView>(R.id.tv_bus_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoritesViewHolder(layoutInflater.inflate(R.layout.row_favorites, parent, false))
    }

    override fun getItemCount() = busList.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.tvBusNumber.text = busList[position].OriginCode
    }
}