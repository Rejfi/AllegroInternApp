package com.example.allegrointernapp.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.allegrointernapp.R
import com.example.allegrointernapp.adapters.OffersAdapter
import com.example.allegrointernapp.viewmodels.ShopViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopFragment : Fragment(), OffersAdapter.OnOfferClickListener{

    private lateinit var shopViewModel: ShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

       shopViewModel = ViewModelProvider(requireActivity()).get(ShopViewModel(requireActivity().application)::class.java)
        Log.e("ViewModel", shopViewModel.hashCode().toString() + ":ShopFragment")


        val noInternetSbar = Snackbar.make(
            swipeLayoutFrag,
            "Check your internet connection and pull to refresh",
            Snackbar.LENGTH_INDEFINITE)

        val noDataSnackbar =  Snackbar.make(
            swipeLayoutFrag,
            "No data to show",
            Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
                if(isOnline()) shopViewModel.refreshData()
                else noInternetSbar.show()
            }


        shopViewModel.getAllOffersLiveData().observe(viewLifecycleOwner, Observer {
                if(it.isNullOrEmpty()){
                    swipeLayoutFrag.isRefreshing = false
                    noDataSnackbar.show()

                }else {
                    recyclerViewFrag.layoutManager = LinearLayoutManager(requireContext())
                    val adapter = OffersAdapter(it, this)
                    recyclerViewFrag.adapter = adapter
                    swipeLayoutFrag.isRefreshing = false
                    noDataSnackbar.dismiss()
                }
        })

        swipeLayoutFrag.setOnRefreshListener {
            if(isOnline()) {
                shopViewModel.refreshData()
                noInternetSbar.dismiss()
            }
            else {
                swipeLayoutFrag.isRefreshing = false
                noDataSnackbar.show()
            }
        }
    }

    override fun onItemClick(position: Int) {
        val offer = shopViewModel.getAllOffersLiveData().value!![position]
        shopViewModel.setSelectedOffer(offer)
        val detailFragment = DetailFragment()
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, detailFragment, "DetailFragment")
            addToBackStack(null)
            commit()
        }


        /*
        TODO("Przerobić aplikacje na fragmenty")
        TODO("Zdecydować kto będzie nasłuchiwał na kliknięcie")
        TODO("Przygotować layout dla fragmentów")

         */
    }

    private fun isOnline(): Boolean{
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}
