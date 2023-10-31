package com.newagedevs.bdbusroute.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newagedevs.bdbusroute.R
import com.newagedevs.bdbusroute.activity.BusDetails
import com.newagedevs.bdbusroute.adapters.BusRecyclerViewAdapter
import com.newagedevs.bdbusroute.models.BusData
import com.orhanobut.hawk.Hawk

class FavouriteFragment : Fragment() {

    private lateinit var emptyText: TextView
    private lateinit var favouriteBusRecyclerView: RecyclerView
    private lateinit var favouriteRecyclerViewAdapter: BusRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Hawk.init(requireContext()).build()

        activity?.let {
            emptyText = it.findViewById(R.id.empty_text)
            favouriteBusRecyclerView = it.findViewById(R.id.favourite_recycler_view)
        }
        favouriteBusRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }


    private fun loadData() {
        val favouriteUrlList:ArrayList<BusData>  = Hawk.get("favouriteBuses", ArrayList())

        if(favouriteUrlList.isEmpty()){
            emptyText.visibility = View.VISIBLE
        }else{
            emptyText.visibility = View.GONE
        }

        favouriteRecyclerViewAdapter = BusRecyclerViewAdapter(requireContext(), favouriteUrlList,
            onItemClick = {
                val intent = Intent(requireContext(), BusDetails::class.java)
                intent.putExtra("data", it)
                intent.putExtra("source", it.routes[0])
                intent.putExtra("destination", it.routes[it.routes.size-1])

                requireActivity().startActivity(intent)

            })
        favouriteBusRecyclerView.adapter = favouriteRecyclerViewAdapter
    }

}