package com.appcessible.boardthebus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appcessible.boardthebus.TimeFormatter
import com.appcessible.boardthebus.databinding.RowBusArrivalBinding
import com.appcessible.boardthebus.model.BusService

class BusArrivalsAdapter(
    private val busArrivalList: List<BusService>,
    private val timeFormatterHelper: TimeFormatter
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class BusArrivalViewHolder(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BusArrivalViewHolder(
            RowBusArrivalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = busArrivalList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bus = busArrivalList[position]
        (holder as BusArrivalViewHolder).bind(bus, timeFormatterHelper)
    }
}