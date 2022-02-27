package com.appcessible.boardthebus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appcessible.boardthebus.TimeFormatter
import com.appcessible.boardthebus.databinding.RowBusArrivalBinding
import com.appcessible.boardthebus.model.BusService

class SearchAdapter(
    private val timeFormatterHelper: TimeFormatter,
    var busList: List<BusService>,
    private val starClickListener: (BusService) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(
        val binding: RowBusArrivalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BusService, timeFormatterHelper: TimeFormatter) {
            binding.apply {
                model = item
                timeFormatter = timeFormatterHelper
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            RowBusArrivalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun updateList(busList: List<BusService>) {
        this.busList = busList
        notifyDataSetChanged()
    }

    override fun getItemCount() = busList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val bus = busList[position]
        holder.binding.ibFavorite.setOnClickListener {
            starClickListener(bus)
            bus.isFavorite = !bus.isFavorite
            notifyItemChanged(position)
        }
        holder.bind(bus, timeFormatterHelper)
    }
}