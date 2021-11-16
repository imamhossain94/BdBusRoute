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
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.newagedevs.bdbusroute.R
import com.newagedevs.bdbusroute.activity.BusDetails
import com.newagedevs.bdbusroute.adapters.BusRecyclerViewAdapter
import com.newagedevs.bdbusroute.models.BusData
import com.orhanobut.hawk.Hawk

class FavouriteFragment : Fragment() {

    private var mInterstitialAd: InterstitialAd? = null

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

        loadInterstitial()
        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadInterstitial()
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
        favouriteBusRecyclerView.adapter = favouriteRecyclerViewAdapter
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