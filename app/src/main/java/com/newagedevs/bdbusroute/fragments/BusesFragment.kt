package com.newagedevs.bdbusroute.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.gson.Gson
import com.newagedevs.bdbusroute.R
import com.newagedevs.bdbusroute.activity.BusDetails
import com.newagedevs.bdbusroute.adapters.BusRecyclerViewAdapter
import com.newagedevs.bdbusroute.models.BusData
import com.newagedevs.bdbusroute.models.BusDataList
import com.newagedevs.bdbusroute.utils.afterTextChanged
import com.newagedevs.bdbusroute.utils.getJsonDataFromAsset

class BusesFragment : Fragment() {
    private var mInterstitialAd: InterstitialAd? = null

    private lateinit var busDataList: BusDataList
    private lateinit var editTextAllBus: AutoCompleteTextView
    private lateinit var allBusRecyclerView: RecyclerView
    private lateinit var allBusRecyclerViewAdapter: BusRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_buses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            editTextAllBus = it.findViewById(R.id.edit_text_all_bus)
            allBusRecyclerView = it.findViewById(R.id.all_bus_recycler_view)
        }
        allBusRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        editTextAllBus.afterTextChanged { data ->

            val k = ArrayList<BusData>()
            for (x in busDataList.data){
                if(x.english.trim().lowercase().contains(data.trim().lowercase()))
                    k.add(x)
            }

            allBusRecyclerViewAdapter.filterBusList(k)
        }

        loadInterstitial()
        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadInterstitial()
        loadData()
    }

    private fun loadData() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "dhaka_local_bus.json")
        busDataList = Gson().fromJson(jsonFileString, BusDataList::class.java) as BusDataList

        allBusRecyclerViewAdapter = BusRecyclerViewAdapter(requireContext(), busDataList.data,
        onItemClick = {
            val intent = Intent(requireContext(), BusDetails::class.java)
            intent.putExtra("data", it)
            intent.putExtra("source", it.routes[0])
            intent.putExtra("destination", it.routes[it.routes.size-1])

            if (mInterstitialAd != null) {
                mInterstitialAd?.fullScreenContentCallback =
                    object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            requireActivity().startActivity(intent)
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError?) {}
                        override fun onAdShowedFullScreenContent() {
                            mInterstitialAd = null
                        }
                    }
                mInterstitialAd?.show(requireActivity())
            } else {
                requireActivity().startActivity(intent)
            }

        })
        allBusRecyclerView.adapter = allBusRecyclerViewAdapter


        val k = mutableSetOf<String>()
        for (x in busDataList.data){
            k.add(x.english)
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, k.toList())
        editTextAllBus.setAdapter(adapter)
    }

    private fun loadInterstitial() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            requireContext(),
            getString(R.string.id_interstitial),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }


}