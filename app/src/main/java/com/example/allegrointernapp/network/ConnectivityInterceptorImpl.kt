package com.example.allegrointernapp.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.allegrointernapp.internal.exceptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor which check internet connection and throws exception
 * @throws NoConnectivityException when internet is not available
 */
class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline()) throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean{
        val cm = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}