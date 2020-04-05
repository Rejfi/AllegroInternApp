package com.example.allegrointernapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allegrointernapp.data.ShopRepository
import com.example.allegrointernapp.data.Offers
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.emptyFlow

class ShopViewModel : ViewModel(){
    private val repository = ShopRepository()
    private var allOffersLiveData = MutableLiveData<Offers>()

    init {
        setOffers()
    }

    private fun setOffers() = runBlocking{
        val offers = repository.getAllOffersAsync().await()
        if (!offers.offers.isNullOrEmpty()) allOffersLiveData.postValue(offers)
        else allOffersLiveData.value = Offers(emptyList())
    }

    fun getAllOffersLiveData(): LiveData<Offers> {
        return allOffersLiveData
    }

}