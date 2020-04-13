package com.example.allegrointernapp.network

import android.content.Context
import com.example.allegrointernapp.internal.exceptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.SocketTimeoutException

/**
 * Interceptor which check internet connection and throws exception
 * @throws NoConnectivityException when internet is not available
 */
class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        //Check if internet connection is available, if not available throw NoConnectivityException
        if(!NetworkChecker.isOnline(appContext)){ throw NoConnectivityException()}

        //If SocketTimeOutException is thrown, convert to NoConnectivityException and handle it later in app
        try {
            chain.proceed(chain.request())
        } catch (e: SocketTimeoutException) {
            throw NoConnectivityException()
        }
        return chain.proceed(chain.request())
    }

}