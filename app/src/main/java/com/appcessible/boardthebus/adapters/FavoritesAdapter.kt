package com.appcessible.boardthebus.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appcessible.boardthebus.R
import com.appcessible.boardthebus.TimeFormatter
import com.appcessible.boardthebus.database.entity.FavoriteBus
import com.appcessible.boardthebus.databinding.RowFavoritesBinding
import com.appcessible.boardthebus.databinding.RowSearchBinding
import com.appcessible.boardthebus.model.BusService

class FavoritesAdapter(
    var busList: List<FavoriteBus>
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    class FavoritesViewHolder(
        val binding: RowFavoritesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavoriteBus) {
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

    fun updateList(busList: List<FavoriteBus>) {
        this.busList = busList
        notifyDataSetChanged()
    }

    override fun getItemCount() = busList.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val bus = busList[position]
        holder.bind(bus)
    }
}