package com.newagedevs.bdbusroute.api.services

import com.newagedevs.bdbusroute.api.models.Category
import com.newagedevs.bdbusroute.api.models.Customer
import com.newagedevs.bdbusroute.api.models.LogIn
import com.newagedevs.bdbusroute.api.models.Slider
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("KEY: 2ipNlTU99NRahaPYXE1foRggptypqFVaLj1DmJov")
    @POST("customers/login")
    fun logInCustomer(@Body newUser: LogIn): Call<ResponseBody>

    @Headers("KEY: 2ipNlTU99NRahaPYXE1foRggptypqFVaLj1DmJov")
    @POST("customers/signup")
    fun signUpCustomer(@Body newUser: Customer): Call<ResponseBody>

    @Headers("KEY: 2ipNlTU99NRahaPYXE1foRggptypqFVaLj1DmJov")
    @GET("sliders")
    fun getSliderList(): Call<ResponseBody>

    @Headers("KEY: 2ipNlTU99NRahaPYXE1foRggptypqFVaLj1DmJov")
    @GET("categories")
    fun getCategoryList(): Call<ResponseBody>

    @Headers("KEY: 2ipNlTU99NRahaPYXE1foRggptypqFVaLj1DmJov")
    @GET("products")
    fun getProductList(): Call<ResponseBody>


}