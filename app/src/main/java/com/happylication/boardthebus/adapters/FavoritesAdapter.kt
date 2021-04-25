package com.happylication.boardthebus.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.happylication.boardthebus.R
import com.happylication.boardthebus.database.entity.FavoriteBus

class FavoritesAdapter(
    var busList: List<FavoriteBus>
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    class FavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvBusNumber = view.findViewById<TextView>(R.id.tv_bus_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoritesViewHolder(layoutInflater.inflate(R.layout.row_favorites, parent, false))
    }

    fun updateList(busList: List<FavoriteBus>) {
        this.busList = busList
        notifyDataSetChanged()
    }

    override fun getItemCount() = busList.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.tvBusNumber.text = busList[position].busNo.toString()
    }
}