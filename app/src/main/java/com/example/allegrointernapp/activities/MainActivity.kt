package com.example.allegrointernapp.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allegrointernapp.adapters.OffersAdapter
import com.example.allegrointernapp.R
import com.example.allegrointernapp.data.Offers
import com.example.allegrointernapp.fragments.DetailFragment
import com.example.allegrointernapp.fragments.ShopFragment
import com.example.allegrointernapp.viewmodels.ShopViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var shopViewModel: ShopViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fm = supportFragmentManager
        val shopFragment = ShopFragment()

        shopViewModel = ViewModelProvider(this)[(ShopViewModel(application)::class.java)]

        if(savedInstanceState == null){
            fm.beginTransaction().apply {
                add(R.id.fragmentContainer, shopFragment, "ShopFragment")
                commit()
            }

        }
    }



}
