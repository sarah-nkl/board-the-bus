package com.appcessible.boardthebus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appcessible.boardthebus.TimeFormatter
import com.appcessible.boardthebus.databinding.RowBusArrivalBinding
import com.appcessible.boardthebus.databinding.RowSearchBinding
import com.appcessible.boardthebus.model.BusService
import com.appcessible.boardthebus.model.SearchResult

class SearchAdapter(
    private val timeFormatterHelper: TimeFormatter,
    private val resultClickListener: (SearchResult) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val resultList: MutableList<SearchResult> = mutableListOf()
    private val busArrivalList: MutableList<BusService> = mutableListOf()

    enum class ViewHolderType(val viewType: Int) {
        SEARCH_RESULT(0), BUS_ARRIVAL(1)
    }

    class SearchViewHolder(
        val binding: RowSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchResult) {
            binding.apply {
                model = item
                executePendingBindings()
            }
        }
    }

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

    override fun getItemViewType(position: Int): Int {
        return if (busArrivalList.isEmpty()) {
            ViewHolderType.SEARCH_RESULT.viewType
        } else {
            ViewHolderType.BUS_ARRIVAL.viewType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewHolderType.SEARCH_RESULT.viewType -> SearchViewHolder(
                RowSearchBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewHolderType.BUS_ARRIVAL.viewType -> BusArrivalViewHolder(
                RowBusArrivalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalStateException("invalid viewType $viewType")
        }
    }

    fun updateResultList(busList: List<SearchResult>) {
        this.resultList.clear()
        this.resultList.addAll(busList)
        this.busArrivalList.clear()
        notifyDataSetChanged()
    }

    fun updateBusArrivalList(busList: List<BusService>?) {
        this.busArrivalList.clear()
        busList?.let {
            this.busArrivalList.addAll(it)
        }
        this.resultList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount() = resultList.size + busArrivalList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ViewHolderType.SEARCH_RESULT.viewType -> {
                val bus = resultList[position]
                (holder as SearchViewHolder).binding.root.setOnClickListener {
                    resultClickListener(bus)
                }
                holder.bind(bus)
            }
            ViewHolderType.BUS_ARRIVAL.viewType -> {
                val bus = busArrivalList[position]
                (holder as BusArrivalViewHolder).bind(bus, timeFormatterHelper)
            }
        }
    }
}