package com.newagedevs.bdbusroute.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newagedevs.bdbusroute.R
import com.newagedevs.bdbusroute.models.BusData


class AllBusRecyclerViewAdapter(
    private val context: Context?,
    private var busList: ArrayList<BusData>,
    //val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_bus, parent, false)
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).bindItems(busList[position])

        holder.itemButton.setOnClickListener {
//            urlList.removeAt(position)
//            Hawk.delete("favouriteUrls")
//            Hawk.put("favouriteUrls", urlList)
            //onDelete(urlList.size)
            //notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return busList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterBusList(filterName: ArrayList<BusData>) {
        this.busList = filterName
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemButton: Button = itemView.findViewById(R.id.item_bus_button)

        @SuppressLint("SetTextI18n")
        fun bindItems(busData: BusData) {

            val busImage = itemView.findViewById<ImageView>(R.id.bus_image)
            val busName = itemView.findViewById<TextView>(R.id.bus_name)
            val busRoute = itemView.findViewById<TextView>(R.id.bus_route)
            busRoute.isSelected = true

            busName.text = busData.english
            busRoute.text = busData.routes.joinToString(separator = " - ")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                busRoute.justificationMode = JUSTIFICATION_MODE_INTER_WORD
            }

            if(busData.image.isNotEmpty())
                Glide.with(itemView).load(busData.image).fitCenter().into(busImage)

        }
    }
}