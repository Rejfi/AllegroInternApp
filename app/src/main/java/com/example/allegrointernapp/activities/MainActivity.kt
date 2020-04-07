package com.example.allegrointernapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allegrointernapp.adapters.OffersAdapter
import com.example.allegrointernapp.R
import com.example.allegrointernapp.data.Offers
import com.example.allegrointernapp.viewmodels.ShopViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OffersAdapter.OnOfferClickListener {

    private lateinit var shopViewModel: ShopViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shopViewModel = ViewModelProvider(this)[(ShopViewModel(application)::class.java)]


        val snackbar =  Snackbar.make(
            swipeRefreshLayout,
            "No connectivity available. Turn on internet",
            Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry") {
                    shopViewModel.refreshData()
            }

            val asyncViewModelData = shopViewModel.getAllOffersLiveData()

            asyncViewModelData.observe(this, Observer {
            if(it.isNullOrEmpty()){
                snackbar.show()
                swipeRefreshLayout.isRefreshing = false

            }else {
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                val adapter = OffersAdapter(it, this)
                recyclerView.adapter = adapter
                swipeRefreshLayout.isRefreshing = false
                snackbar.dismiss()
                }
            })

        /**
         * Swipe to refresh data and set refreshing icon till completed network request
         */
        swipeRefreshLayout.setOnRefreshListener {
            shopViewModel.refreshData()
        }

    }

    /**
     * Open DetailActivity and send offer data via Intent.putParcelable
     */
    override fun onItemClick(position: Int) {
        val offer = shopViewModel.getAllOffersLiveData().value!![position]
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra("offerDetails", offer)
        startActivity(intent)
    }


}
