package com.newagedevs.bdbusroute.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newagedevs.bdbusroute.R
import com.newagedevs.bdbusroute.adapter.catagory.CategoryAdapter
import com.newagedevs.bdbusroute.adapter.slider.SliderAdapter
import com.newagedevs.bdbusroute.adapter.wish.WishAdapter
import com.newagedevs.bdbusroute.api.models.product.Item
import com.newagedevs.bdbusroute.helper.DatabaseHelper

class WishFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wish, container, false)
    }

    private lateinit var wishAdapter: WishAdapter
    private lateinit var wishRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            wishRecyclerView = it.findViewById(R.id.wish_recycler_view)
        }
        wishRecyclerView.layoutManager = LinearLayoutManager(context)


        val database = DatabaseHelper(requireContext())
        val lists = database.getWishList()

        wishAdapter = WishAdapter(context, lists, moveBagListener = {

            wishMoveClickListeners(it)

        }, removeListener = {

            wishRemoveClickListeners(it.id.toString())

        })
        wishRecyclerView.adapter = wishAdapter

    }


    private fun wishMoveClickListeners(product: Item){



    }

    private fun wishRemoveClickListeners(product_id:String){

        DatabaseHelper(requireContext()).removeWishById(product_id)
        wishAdapter.filterWishList(DatabaseHelper(requireContext()).getWishList())

    }


}