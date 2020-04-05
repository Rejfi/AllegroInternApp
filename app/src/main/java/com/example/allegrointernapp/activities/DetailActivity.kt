package com.example.allegrointernapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.allegrointernapp.R
import com.example.allegrointernapp.data.Offer

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if(intent.hasExtra("offerDetails")){
            val offer = intent.getParcelableExtra<Offer>("offerDetails")!!
            title = offer.name
        }
    }
}
