package com.example.allegrointernapp.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.allegrointernapp.data.Offers
import com.example.allegrointernapp.interfaces.ApiAllegro
import com.example.allegrointernapp.internal.exceptions.NoConnectivityException
import com.example.allegrointernapp.network.ConnectivityInterceptor
import com.example.allegrointernapp.network.ConnectivityInterceptorImpl
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import java.lang.Appendable
import java.lang.Exception

class ShopRepository(app: Application) {
    private val api = ApiAllegro(ConnectivityInterceptorImpl(app.applicationContext))
    private val appContext = app.applicationContext
    private val allOffersLiveData = MutableLiveData<List<Offer>>()

}