package com.newagedevs.bdbusroute.models

import com.google.gson.annotations.SerializedName

data class BusDataList (
	@SerializedName("data") val data : ArrayList<BusData>
)