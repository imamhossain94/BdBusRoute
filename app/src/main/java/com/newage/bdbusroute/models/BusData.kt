package com.newage.bdbusroute.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BusData(
    @SerializedName("english") val english : String,
    @SerializedName("bangle") val bangle : String,
    @SerializedName("image") val image : String,
    @SerializedName("routes") val routes : ArrayList<String>,
    @SerializedName("time") val time : String,
    @SerializedName("service_type") val service_type : String
):Serializable