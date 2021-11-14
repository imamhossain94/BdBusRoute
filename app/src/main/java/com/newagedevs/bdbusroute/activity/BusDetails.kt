package com.newagedevs.bdbusroute.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.jsibbold.zoomage.ZoomageView
import com.newagedevs.bdbusroute.R
import com.newagedevs.bdbusroute.models.BusData
import com.newagedevs.bdbusroute.utils.toastySuccess
import com.orhanobut.hawk.Hawk
import android.content.Intent

import android.content.ActivityNotFoundException
import android.net.Uri
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newagedevs.bdbusroute.adapters.BusRecyclerViewAdapter
import com.newagedevs.bdbusroute.adapters.RouteRecyclerViewAdapter


class BusDetails : AppCompatActivity() {

    private var isFavourite:Boolean = false

    private lateinit var backButton:ImageView
    private lateinit var toolbarTitle:TextView
    private lateinit var heartButton:ImageView
    private lateinit var busImageView: ZoomageView
    private lateinit var sourceText:TextView
    private lateinit var destinationText:TextView
    private lateinit var buttonOpenMap:Button
    private lateinit var routesRecyclerView: RecyclerView
    private lateinit var routesRecyclerViewAdapter: RouteRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_details)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Hawk.init(this).build()
        @RequiresApi(Build.VERSION_CODES.M)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        backButton = findViewById(R.id.icon_back)
        toolbarTitle = findViewById(R.id.toolbar_title)
        heartButton = findViewById(R.id.icon_heart)
        busImageView = findViewById(R.id.bus_image_details)
        sourceText = findViewById(R.id.source_text)
        destinationText = findViewById(R.id.destination_text)
        buttonOpenMap = findViewById(R.id.button_open_map)
        routesRecyclerView = findViewById(R.id.routes_recycler_view)
        routesRecyclerView.layoutManager = LinearLayoutManager(this)
        routesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        val busData = intent.getSerializableExtra("data") as? BusData
        val source = intent.getStringExtra("source")
        val destination = intent.getStringExtra("destination")

        toolbarTitle.text = busData!!.english

        var favouriteUrlList:ArrayList<BusData> = Hawk.get("favouriteBuses", ArrayList())

        if(busData in favouriteUrlList)
            isFavourite = true

        if(isFavourite){
            heartButton.setImageResource(R.drawable.ic_heart_field)
        }else{
            heartButton.setImageResource(R.drawable.ic_heart_stroke)
        }

        if(busData.image.isNotEmpty())
            Glide.with(this).load(busData.image).fitCenter().into(busImageView)

        sourceText.text = source
        destinationText.text = destination

        backButton.setOnClickListener{
            finish()
        }

        heartButton.setOnClickListener{
            if(isFavourite){
                favouriteUrlList = Hawk.get("favouriteBuses", ArrayList())
                favouriteUrlList.removeAt(favouriteUrlList.indexOf(busData))
                Hawk.delete("favouriteBuses")
                Hawk.put("favouriteBuses", favouriteUrlList)
                this.toastySuccess("Removed from favourite list")
                heartButton.setImageResource(R.drawable.ic_heart_stroke)
            }else{
                favouriteUrlList = Hawk.get("favouriteBuses", ArrayList())
                favouriteUrlList.add(busData)
                Hawk.delete("favouriteBuses")
                Hawk.put("favouriteBuses", favouriteUrlList)
                this.toastySuccess("Added to favourite list")
                heartButton.setImageResource(R.drawable.ic_heart_field)
            }
            isFavourite = !isFavourite
        }

        buttonOpenMap.setOnClickListener{

            try {
                val uri: Uri = Uri.parse("https://www.google.co.in/maps/dir/$source/$destination")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.apps.maps")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } catch (exception: ActivityNotFoundException) {
                val uri: Uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

        }

        routesRecyclerViewAdapter = RouteRecyclerViewAdapter(this,  busData.routes)
        routesRecyclerView.adapter = routesRecyclerViewAdapter

    }
}