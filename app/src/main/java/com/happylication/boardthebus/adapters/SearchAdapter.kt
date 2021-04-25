package com.happylication.boardthebus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.happylication.boardthebus.TimeFormatter
import com.happylication.boardthebus.databinding.RowSearchBinding
import com.happylication.boardthebus.model.BusService

class SearchAdapter(
    val timeFormatterHelper: TimeFormatter,
    var busList: List<BusService>,
    private val starClickListener: (BusService) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(
        val binding: RowSearchBinding
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
            RowSearchBinding.inflate(
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
            starClickListener.invoke(bus)
        }
        holder.bind(bus, timeFormatterHelper)
    }
}