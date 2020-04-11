package com.example.allegrointernapp.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.allegrointernapp.data.data_model.Offer
import com.example.allegrointernapp.data.data_model.Offers
import com.example.allegrointernapp.interfaces.ApiAllegro
import com.example.allegrointernapp.network.ConnectivityInterceptorImpl
import kotlinx.coroutines.Deferred
import java.lang.Exception

class ShopRepository(app: Application) {
    private val api = ApiAllegro(ConnectivityInterceptorImpl(app))
    fun getOffersAsync(): Deferred<Offers>?{
        return try {
            api.getAllOffersAsync()
        }catch (e: Exception){
            null
        }
    }




}