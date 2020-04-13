package com.example.allegrointernapp.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
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
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if(savedInstanceState == null){
            fm.beginTransaction().apply {
                add(R.id.fragmentContainer, shopFragment, "ShopFragment")
                commit()
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
              onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onBackPressed() {
        Log.d("onBackPressed", "onBackPressed method call")
        super.onBackPressed()
    }
}
