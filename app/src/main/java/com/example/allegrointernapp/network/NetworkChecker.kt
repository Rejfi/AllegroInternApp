package com.example.allegrointernapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest


class NetworkChecker{
    companion object{
        //Function checks internet connection availability
        fun isOnline(context: Context): Boolean{
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }
}
}