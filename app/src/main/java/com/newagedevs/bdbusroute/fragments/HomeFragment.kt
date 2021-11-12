package com.newagedevs.bdbusroute.fragments

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.gson.reflect.TypeToken
import com.newagedevs.bdbusroute.R
import com.newagedevs.bdbusroute.adapter.catagory.CategoryAdapter
import com.newagedevs.bdbusroute.adapter.product.ProductAdapter
import com.newagedevs.bdbusroute.adapter.slider.SliderAdapter
import com.newagedevs.bdbusroute.api.models.Category
import com.newagedevs.bdbusroute.api.models.Slider
import com.newagedevs.bdbusroute.api.models.product.Item
import com.newagedevs.bdbusroute.api.services.ApiBuilder
import com.newagedevs.bdbusroute.api.services.ApiService
import com.newagedevs.bdbusroute.helper.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {


        }





    }




    override fun onResume() {
        super.onResume()
        loadData()
    }



}