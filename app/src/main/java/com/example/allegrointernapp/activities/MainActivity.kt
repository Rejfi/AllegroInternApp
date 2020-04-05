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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OffersAdapter.OnOfferClickListener {

    private lateinit var shopViewModel: ShopViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shopViewModel = ViewModelProvider(this).get(ShopViewModel::class.java)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)


        val asyncViewModelData = shopViewModel.getAllOffersLiveData()
        asyncViewModelData.observe(this, Observer {
            val adapter = OffersAdapter(it, this)
            recyclerView.adapter = adapter
        })

    }

    /**
     * Open DetailActivity and send offer data via Intent.putParcelable
     */
    override fun onItemClick(position: Int) {
        val offer = shopViewModel.getAllOffersLiveData().value!!.offers[position]
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra("offerDetails", offer)
        startActivity(intent)
    }


}
