package com.example.allegrointernapp.data

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.allegrointernapp.interfaces.ApiAllegro
import com.example.allegrointernapp.network.ConnectivityInterceptorImpl

class ShopRepository(app: Application) {
    private val api = ApiAllegro(ConnectivityInterceptorImpl(app.applicationContext))
    private val appContext = app.applicationContext
    private val allOffersLiveData = MutableLiveData<List<Offer>>()

}