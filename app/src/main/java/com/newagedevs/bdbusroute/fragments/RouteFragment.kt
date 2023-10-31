package com.newagedevs.bdbusroute.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.newagedevs.bdbusroute.R
import com.newagedevs.bdbusroute.activity.BusDetails
import com.newagedevs.bdbusroute.adapters.BusRecyclerViewAdapter
import com.newagedevs.bdbusroute.models.BusData
import com.newagedevs.bdbusroute.models.BusDataList
import com.newagedevs.bdbusroute.utils.afterTextChanged
import com.newagedevs.bdbusroute.utils.getJsonDataFromAsset


class RouteFragment : Fragment() {

    private var source: String = ""
    private var destination: String = ""

    private lateinit var busDataList: BusDataList
    private lateinit var sourceEditText: AutoCompleteTextView
    private lateinit var destinationEditText: AutoCompleteTextView
    private lateinit var searchResultBusRecyclerView: RecyclerView
    private lateinit var searchResultRecyclerViewAdapter: BusRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_route, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            sourceEditText = it.findViewById(R.id.edit_text_source)
            destinationEditText = it.findViewById(R.id.edit_text_destination)
            searchResultBusRecyclerView = it.findViewById(R.id.search_result_recycler_view)
        }
        searchResultBusRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        sourceEditText.afterTextChanged { data ->
            source = data
            findRoute()
        }

        destinationEditText.afterTextChanged { data ->
            destination = data
            findRoute()
        }

        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "dhaka_local_bus.json")
        busDataList = Gson().fromJson(jsonFileString, BusDataList::class.java) as BusDataList
        searchResultRecyclerViewAdapter = BusRecyclerViewAdapter(requireContext(), busDataList.data,
            onItemClick = {
                val intent = Intent(requireContext(), BusDetails::class.java)
                intent.putExtra("data", it)
                if (source.isNotEmpty() || destination.isNotEmpty()) {
                    intent.putExtra("source", source)
                    intent.putExtra("destination", destination)
                } else {
                    intent.putExtra("source", it.routes[0])
                    intent.putExtra("destination", it.routes[it.routes.size - 1])
                }
                //Code here
                requireActivity().startActivity(intent)

            })
        searchResultBusRecyclerView.adapter = searchResultRecyclerViewAdapter

        val k = mutableSetOf<String>()
        for (x in busDataList.data) {
            for (y in x.routes) {
                k.add(y)
            }
        }
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, k.toList())
        sourceEditText.setAdapter(adapter)
        destinationEditText.setAdapter(adapter)
    }

    private fun findRoute() {
        val k = ArrayList<BusData>()
        for (x in busDataList.data) {

            if (source in x.routes && destination in x.routes)
                k.add(x)
//            for (y in x.routes){
//                if(y.trim().lowercase().contains(source.trim().lowercase())
//                    && y.trim().lowercase().contains(destination.trim().lowercase()))
//                    k.add(x)
//            }
        }
        searchResultRecyclerViewAdapter.filterBusList(k)
    }


}