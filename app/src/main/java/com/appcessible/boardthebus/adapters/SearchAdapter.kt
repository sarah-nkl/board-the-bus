package com.appcessible.boardthebus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appcessible.boardthebus.TimeFormatter
import com.appcessible.boardthebus.databinding.RowBusArrivalBinding
import com.appcessible.boardthebus.databinding.RowSearchBusServiceBinding
import com.appcessible.boardthebus.databinding.RowSearchBusStopBinding
import com.appcessible.boardthebus.model.BusService
import com.appcessible.boardthebus.model.SearchResult
import com.appcessible.boardthebus.model.SearchResultLabel

class SearchAdapter(
    private val timeFormatterHelper: TimeFormatter,
    private val resultClickListener: (SearchResult) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val resultList: MutableList<SearchResult> = mutableListOf()
    private val busArrivalList: MutableList<BusService> = mutableListOf()

    enum class ViewHolderType(val viewType: Int) {
        SEARCH_RESULT_BUS_SERVICE(0), SEARCH_RESULT_BUS_STOP(1), BUS_ARRIVAL(2)
    }

    class SearchBusServiceViewHolder(
        val binding: RowSearchBusServiceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchResult) {
            binding.apply {
                model = item
                executePendingBindings()
            }
        }
    }

    class SearchBusStopViewHolder(
        val binding: RowSearchBusStopBinding
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
        return if (busArrivalList.isNotEmpty()) {
            ViewHolderType.BUS_ARRIVAL.viewType
        } else if (resultList[position].label == SearchResultLabel.BUS_SERVICE) {
            ViewHolderType.SEARCH_RESULT_BUS_SERVICE.viewType
        } else {
            ViewHolderType.SEARCH_RESULT_BUS_STOP.viewType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewHolderType.SEARCH_RESULT_BUS_SERVICE.viewType -> SearchBusServiceViewHolder(
                RowSearchBusServiceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewHolderType.SEARCH_RESULT_BUS_STOP.viewType -> SearchBusStopViewHolder(
                RowSearchBusStopBinding.inflate(
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
            ViewHolderType.SEARCH_RESULT_BUS_SERVICE.viewType -> {
                val result = resultList[position]
//                (holder as SearchBusServiceViewHolder).binding.apply {
//                    root.setOnClickListener {
//                        resultClickListener(result)
//                    }
//                }
                (holder as SearchBusServiceViewHolder).bind(result)
            }
            ViewHolderType.SEARCH_RESULT_BUS_STOP.viewType -> {
                val result = resultList[position]
                (holder as SearchBusStopViewHolder).binding.apply {
                    root.setOnClickListener {
                        resultClickListener(result)
                    }
                }
                holder.bind(result)
            }
            ViewHolderType.BUS_ARRIVAL.viewType -> {
                val bus = busArrivalList[position]
                (holder as BusArrivalViewHolder).bind(bus, timeFormatterHelper)
            }
        }
    }
}