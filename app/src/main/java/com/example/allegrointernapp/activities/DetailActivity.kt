package com.example.allegrointernapp.activities

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.allegrointernapp.R
import com.example.allegrointernapp.data.Offer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if(intent.hasExtra("offerDetails")){
            val offer = intent.getParcelableExtra<Offer>("offerDetails")!!

            CoroutineScope(Dispatchers.Main).launch {
                Picasso.get().load(offer.thumbnailUrl).into(detailImage)
            }
            title = offer.name
            detailDescription.text = offer.description
            val totalPrice = "${offer.price.amount}  ${offer.price.currency}"
            detailPrice.text = totalPrice
        }

    }

}
