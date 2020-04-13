package com.example.allegrointernapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.allegrointernapp.R
import com.example.allegrointernapp.data.data_model.Offer
import com.example.allegrointernapp.viewmodels.ShopViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private lateinit var shopViewModel: ShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(false)
        requireActivity().actionBar?.setHomeButtonEnabled(false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        shopViewModel = ViewModelProvider(requireActivity()).get(ShopViewModel(requireActivity().application)::class.java)

        shopViewModel.getSelectedOffer().observe(viewLifecycleOwner, Observer {
            Picasso.get().load(it.thumbnailUrl).fit().centerInside().into(detailImageFrag)
            showOfferContent(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().title = "Sklep Allegro"
    }

    private fun showOfferContent(offer: Offer){
        requireActivity().title = offer.name
        val totalPrice = "${offer.price.amount}  ${offer.price.currency}"
        detailPriceFrag.text = totalPrice
        detailDescriptionFrag.text = offer.description
    }
}
