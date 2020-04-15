package com.example.allegrointernapp.interfaces

import com.example.allegrointernapp.data.data_model.Offers
import com.example.allegrointernapp.network.ConnectivityInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/**
* Interface to use network request with Retrofit2
*/
interface ApiAllegro {
    @GET("offers")
    fun getAllOffersAsync(): Deferred<Offers> //Use Deferred because Retrofit since ver.2.6.0 allows correlating with Kotlin Coroutines

    companion object{
        //Override invoke method to call Api short way
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): ApiAllegro {

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(30,TimeUnit.SECONDS) //Set higher then default readTimeout because photos on server are in high resolution
                .protocols(listOf(Protocol.HTTP_1_1))
                .addInterceptor(connectivityInterceptor)
                .build()

            //Prepare Retrofit object
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://private-987cdf-allegromobileinterntest.apiary-mock.com/allegro/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory()) //
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiAllegro::class.java)
        }
    }

}
