package com.appcessible.boardthebus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appcessible.boardthebus.database.entity.BusStop
import com.appcessible.boardthebus.databinding.RowBusstopBinding

class BusStopAdapter(
    var busStopList: List<BusStop>,
    private val starClickListener: (BusStop) -> Unit
) : RecyclerView.Adapter<BusStopAdapter.SearchViewHolder>() {

    class SearchViewHolder(
        val binding: RowBusstopBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BusStop) {
            binding.apply {
                model = item
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            RowBusstopBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun updateList(busStopList: List<BusStop>) {
        this.busStopList = busStopList
        notifyDataSetChanged()
    }

    override fun getItemCount() = busStopList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val busStop = busStopList[position]
        holder.binding.ibFavorite.setOnClickListener {
            starClickListener(busStop)
            busStop.isFavorite = !busStop.isFavorite
            notifyItemChanged(position)
        }
        holder.bind(busStop)
    }
}