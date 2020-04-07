package com.example.allegrointernapp.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allegrointernapp.data.Offer
import com.example.allegrointernapp.data.ShopRepository
import com.example.allegrointernapp.data.Offers
import com.example.allegrointernapp.interfaces.ApiAllegro
import com.example.allegrointernapp.internal.exceptions.NoConnectivityException
import com.example.allegrointernapp.network.ConnectivityInterceptorImpl
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.emptyFlow

class ShopViewModel(app: Application): AndroidViewModel(app){
    private val repository = ShopRepository(app)
    private val allOffersLiveData = MutableLiveData<List<Offer>>()

    init {
        setOffers()
    }

    fun getAllOffersLiveData(): LiveData<List<Offer>> {
        return allOffersLiveData
    }

    private fun setOffers() = runBlocking{
       // val offers = repository.getAllOffersAsync().await().offers
        val api = ApiAllegro(ConnectivityInterceptorImpl(getApplication()))
        val offers = try {
            api.getAllOffersAsync().await().offers

        }catch (e: NoConnectivityException){
            Log.d("Connectivity", e.message, e)
            emptyList<Offer>()
        }
        allOffersLiveData.value = offers
                .filter{ it.price.amount.toDouble() in 50.0..1000.0 }
                .sortedBy { it.price.amount.toDouble()
            }
    }

    fun refreshData(){
        setOffers()
    }

}