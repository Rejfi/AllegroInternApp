package com.example.allegrointernapp.interfaces

import com.example.allegrointernapp.data.data_model.Offers
import com.example.allegrointernapp.network.ConnectivityInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.HTTP
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

/**
* Interface to use network request with Retrofit
*/
interface ApiAllegro {

    @Throws(SocketTimeoutException::class)
    @GET("offers")
    fun getAllOffersAsync(): Deferred<Offers>

    companion object{
        //Override invoke method to call Api short way
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): ApiAllegro {

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .protocols(listOf(Protocol.HTTP_1_1))
                .addInterceptor(connectivityInterceptor)
                .build()

            //Prepare Retrofit object
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://private-987cdf-allegromobileinterntest.apiary-mock.com/allegro/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiAllegro::class.java)
        }
    }

}
