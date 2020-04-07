package com.example.allegrointernapp.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.allegrointernapp.data.Offers
import com.example.allegrointernapp.interfaces.ApiAllegro
import com.example.allegrointernapp.internal.exceptions.NoConnectivityException

class OfferDataSourceImpl(private val apiAllegro: ApiAllegro) : OfferDataSource {

    private val __downloadedOfferData = MutableLiveData<Offers>()

    override val downloadedOffers: LiveData<Offers>
        get() = __downloadedOfferData

    override suspend fun fetchData() {
        try {
            val fetchedData = apiAllegro
                .getAllOffersAsync()
                .await()
            __downloadedOfferData.postValue(fetchedData)
        }catch (e: NoConnectivityException){
            Log.e("connectivity", "No internet", e)
        }
    }
}