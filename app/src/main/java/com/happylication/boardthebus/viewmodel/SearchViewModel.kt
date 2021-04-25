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
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.happylication.boardthebus.BusArrivalService
import com.happylication.boardthebus.R
import com.happylication.boardthebus.database.AppDatabase
import com.happylication.boardthebus.model.BusArrival
import com.happylication.boardthebus.model.NextBus
import com.happylication.boardthebus.model.mockNextBus
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val busArrivalService: BusArrivalService
) : ViewModel() {

    val adapter = SearchAdapter(emptyList())

    suspend fun search(queryString: String): BusArrival {
        return busArrivalService.getBusArrivalByBus(queryString)
    }
}

class SearchAdapter(
    var busList: List<NextBus>
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvBusNumber = view.findViewById<TextView>(R.id.tv_bus_number)
        val tvBusArrival = view.findViewById<TextView>(R.id.tv_bus_timing)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchViewHolder(layoutInflater.inflate(R.layout.row_search, parent, false))
    }

    fun updateList(busList: List<NextBus>) {
        this.busList = busList
        notifyDataSetChanged()
    }

    override fun getItemCount() = busList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.tvBusNumber.text = busList[position].OriginCode
        holder.tvBusArrival.text = busList[position].EstimatedArrival
    }
}