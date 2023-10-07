package com.appcessible.boardthebus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appcessible.boardthebus.database.entity.BusStop
import com.appcessible.boardthebus.databinding.RowFavoritesBinding

class FavoritesAdapter(
    private var busStopList: List<BusStop>
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

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
        this.busStopList = busStopList
        notifyDataSetChanged()
    }

    override fun getItemCount() = busStopList.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val busStop = busStopList[position]
        holder.bind(busStop)
    }
}