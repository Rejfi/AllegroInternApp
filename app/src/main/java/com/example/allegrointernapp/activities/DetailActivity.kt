package com.example.allegrointernapp.activities

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.FontResourcesParserCompat
import com.example.allegrointernapp.R
import com.example.allegrointernapp.data.Offer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.lang.Exception


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if(intent.hasExtra("offerDetails")){
            val offer = intent.getParcelableExtra<Offer>("offerDetails")!!
            CoroutineScope(Dispatchers.Main).launch {
                Picasso.get().load(offer.thumbnailUrl).fit().centerInside().into(detailImage)
            }
            title = offer.name
            val totalPrice = "${offer.price.amount}  ${offer.price.currency}"
            detailPrice.text = totalPrice
            prepareHtml(offer.description)
        }


    }

    private fun prepareHtml(html: String?){
        if(!html.isNullOrEmpty()){
            val doc = Jsoup.parse(html)
            try{
                val header = doc.selectFirst("h1")
               // detailDescriptionHeader.text = header.text()
               // detailDescriptionHeader.textSize = 25f
                val tv = TextView(applicationContext)
                tv.text = header.text()
                tv.textSize = 25f
                tv.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)
                detailContent.addView(tv)
            }catch (e: Exception){
                Log.d("JSOUP", e.message + "|| znacznik h2")
            }
            try {
                val description = doc.select("p")
                val tv = TextView(applicationContext)
                tv.text = description.text()
                tv.textSize = 20f
                detailContent.addView(tv)
            }catch (e: Exception){
                Log.d("JSOUP", e.message + "|| znacznik p")
            }

        }
        /*
        TODO("Ogarnąć parsowanie HTML aby wyświetlało w przystępnej formie znacznki odpowiednie")
    TODO("Przygotować layout dla wszystkich orientacji urządzenia")
    TODO("Wprowadzić wyszukiwanie w ActionBar")
        */
    }

}

