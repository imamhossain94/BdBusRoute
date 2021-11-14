package com.newagedevs.bdbusroute.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newagedevs.bdbusroute.R
import com.newagedevs.bdbusroute.adapters.AllBusRecyclerViewAdapter
import com.newagedevs.bdbusroute.models.BusData
import com.newagedevs.bdbusroute.models.BusDataList
import com.newagedevs.bdbusroute.utils.getJsonDataFromAsset

class BusesFragment : Fragment() {

    private lateinit var editTextAllBus: EditText
    private lateinit var allBusRecyclerView: RecyclerView
    private lateinit var allBusRecyclerViewAdapter: AllBusRecyclerViewAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_buses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            editTextAllBus = it.findViewById(R.id.edit_text_all_bus)
            allBusRecyclerView = it.findViewById(R.id.all_bus_recycler_view)
        }
        allBusRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        editTextAllBus
        loadData()
    }


    private fun loadData() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "dhaka_local_bus.json")

        val routine = Gson().fromJson(jsonFileString, BusDataList::class.java) as BusDataList

        allBusRecyclerViewAdapter = AllBusRecyclerViewAdapter(requireContext(), routine.data)
        allBusRecyclerView.adapter = allBusRecyclerViewAdapter
    }

}