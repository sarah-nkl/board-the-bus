package com.appcessible.boardthebus.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appcessible.boardthebus.database.entity.BusStop
import com.appcessible.boardthebus.databinding.RowFavoritesBinding
import com.appcessible.boardthebus.model.SearchResult

class FavoritesAdapter(
    private val starClickListener: (String) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private val busStopList: MutableList<BusStop> = mutableListOf()

    class FavoritesViewHolder(
        val binding: RowFavoritesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BusStop) {
            binding.apply {
                model = item
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            RowFavoritesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun updateList(busStopList: List<BusStop>) {
        this.busStopList.clear()
        this.busStopList.addAll(busStopList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = busStopList.size

    override fun onBindViewHolder(
        holder: FavoritesViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val busStop = busStopList[position]
        holder.binding.ibFavorite.setOnClickListener {
            starClickListener(busStop.busStopNo)
            busStopList.remove(busStop)
            if (busStopList.isEmpty()) {
                notifyDataSetChanged()
            } else {
                notifyItemRemoved(holder.adapterPosition)
            }
        }
        holder.bind(busStop)
    }
}