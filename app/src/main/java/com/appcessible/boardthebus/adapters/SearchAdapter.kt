package com.appcessible.boardthebus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appcessible.boardthebus.databinding.RowSearchBinding
import com.appcessible.boardthebus.model.SearchResult

class SearchAdapter(
    var resultList: List<SearchResult>,
    private val resultClickListener: (SearchResult) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            RowSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun updateList(busList: List<SearchResult>) {
        this.resultList = busList
        notifyDataSetChanged()
    }

    override fun getItemCount() = resultList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val bus = resultList[position]
        holder.binding.root.setOnClickListener {
            resultClickListener(bus)
        }
        holder.bind(bus)
    }
}