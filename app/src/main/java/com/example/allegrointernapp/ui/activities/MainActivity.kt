package com.example.allegrointernapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.allegrointernapp.R
import com.example.allegrointernapp.ui.fragments.ShopFragment
import com.example.allegrointernapp.viewmodels.ShopViewModel

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
