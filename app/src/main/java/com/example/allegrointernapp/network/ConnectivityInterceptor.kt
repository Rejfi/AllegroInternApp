package com.example.allegrointernapp.network

import com.example.allegrointernapp.internal.exceptions.NoConnectivityException
import okhttp3.Interceptor

/**
 * Interceptor which check internet connection and throws exception
 * @throws NoConnectivityException when internet is not available
 */
interface ConnectivityInterceptor: Interceptor