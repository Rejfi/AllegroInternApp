package com.example.allegrointernapp.data

import com.example.allegrointernapp.data.Offers
import com.example.allegrointernapp.interfaces.ApiAllegro
import kotlinx.coroutines.Deferred
import java.lang.Exception

class ShopRepository {
    private val api = ApiAllegro()
    private var allOffers: Deferred<Offers>
    init {
            allOffers = api.getAllOffersAsync()
    }

    fun getAllOffersAsync(): Deferred<Offers> = allOffers

}