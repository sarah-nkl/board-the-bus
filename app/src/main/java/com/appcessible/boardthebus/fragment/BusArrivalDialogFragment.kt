package com.appcessible.boardthebus.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appcessible.boardthebus.R
import com.appcessible.boardthebus.TimeFormatter
import com.appcessible.boardthebus.adapters.BusArrivalsAdapter
import com.appcessible.boardthebus.databinding.FragmentBusArrivalBinding
import com.appcessible.boardthebus.model.BusService
import com.appcessible.boardthebus.model.BusStops
import com.appcessible.boardthebus.viewmodel.BusArrivalsViewModel
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class BusArrivalDialogFragment : DaggerDialogFragment() {

    @Inject
    lateinit var timeFormatter: TimeFormatter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val busStop: BusStops = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(BUS_STOP_KEY, BusStops::class.java)
        } else {
            requireArguments().getParcelable(BUS_STOP_KEY)
        } ?: throw IllegalStateException("BusStop cannot be null")

        val binding = FragmentBusArrivalBinding.inflate(inflater, container, false).apply {
            model = BusArrivalsViewModel(busStop, ::dismiss)
        }

        val busStopList: List<BusService>? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelableArrayList(BUS_ARRIVAL_LIST_KEY, BusService::class.java)
        } else {
            arguments?.getParcelableArrayList(BUS_ARRIVAL_LIST_KEY)
        }

        val adapter = BusArrivalsAdapter(busStopList ?: emptyList(), timeFormatter)

        binding.adapter = adapter

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }

    companion object {
        const val TAG = "BusArrivalDialog"
        const val BUS_STOP_KEY = "bus_stop_key"
        const val BUS_ARRIVAL_LIST_KEY = "bus_arrival_list_key"
    }
}