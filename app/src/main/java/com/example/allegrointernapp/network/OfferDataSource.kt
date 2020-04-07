package com.example.allegrointernapp.network

import androidx.lifecycle.LiveData
import com.example.allegrointernapp.data.Offers

interface OfferDataSource {
    val downloadedOffers: LiveData<Offers>
    suspend fun fetchData()
}