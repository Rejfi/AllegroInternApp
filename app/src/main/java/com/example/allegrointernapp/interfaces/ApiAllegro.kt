package com.example.allegrointernapp.interfaces

import com.example.allegrointernapp.data.Offers
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiAllegro {

    //https://private-987cdf-allegromobileinterntest.apiary-mock.com/allegro/offers

    @GET("offers")
    fun getAllOffersAsync():Deferred<Offers>
    companion object{
        operator fun invoke(): ApiAllegro {
            return Retrofit.Builder()
                .baseUrl("https://private-987cdf-allegromobileinterntest.apiary-mock.com/allegro/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiAllegro::class.java)
        }
    }

}