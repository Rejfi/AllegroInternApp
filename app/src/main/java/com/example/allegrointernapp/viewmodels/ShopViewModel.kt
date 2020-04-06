package com.example.allegrointernapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allegrointernapp.data.Offer
import com.example.allegrointernapp.data.ShopRepository
import com.example.allegrointernapp.data.Offers
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.emptyFlow

class ShopViewModel: ViewModel(){
    private val repository = ShopRepository()
    private var allOffersLiveData = MutableLiveData<List<Offer>>()

    init {
        setOffers()
    }

    fun getAllOffersLiveData(): LiveData<List<Offer>> {
        return allOffersLiveData
    }

    private fun setOffers() = runBlocking{
        val offers = repository.getAllOffersAsync().await().offers
        if (!offers.isNullOrEmpty()) {
            allOffersLiveData.value = offers
                .filter{ it.price.amount.toDouble() in 50.0..1000.0 }
                .sortedBy { it.price.amount.toDouble()
            }
        }
    }

    fun refreshData(){
        setOffers()
    }

}