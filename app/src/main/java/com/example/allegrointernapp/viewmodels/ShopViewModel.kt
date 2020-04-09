package com.example.allegrointernapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.allegrointernapp.data.data_model.Offer
import com.example.allegrointernapp.interfaces.ApiAllegro
import com.example.allegrointernapp.internal.exceptions.NoConnectivityException
import com.example.allegrointernapp.network.ConnectivityInterceptorImpl
import kotlinx.coroutines.*

/**
 * ViewModel exposes data to Fragments
 */
class ShopViewModel(app: Application): AndroidViewModel(app){
    private val allOffersLiveData = MutableLiveData<List<Offer>>()
    private val selectedOffer = MutableLiveData<Offer>()

    init {
        setOffers()
    }

    fun getAllOffersLiveData(): LiveData<List<Offer>> {
        return allOffersLiveData
    }

    fun setSelectedOffer(offer: Offer){
        selectedOffer.postValue(offer)
    }

    fun getSelectedOffer(): LiveData<Offer>{
        return selectedOffer
    }

    /**
     * Function make asynchronous request via interface ApiAllegro,
     * If data exists filter and set them in LiveData @see allOffersLiveData
     * Otherwise set empty list
     */
    private fun setOffers() = CoroutineScope(viewModelScope.coroutineContext).launch{
            val api = ApiAllegro(ConnectivityInterceptorImpl(getApplication()))
            val offers = try {
                api.getAllOffersAsync().await().offers
            }catch (e: NoConnectivityException){
                Log.e("Connectivity", e.message, e)
                emptyList<Offer>()
            }
            allOffersLiveData.value = offers
                .filter{ it.price.amount.toDouble() in 50.0..1000.0 }
                .sortedBy { it.price.amount.toDouble()
                }
    }

    fun refreshData(){
        setOffers()
    }

}