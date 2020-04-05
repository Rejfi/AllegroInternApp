package com.example.allegrointernapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.allegrointernapp.R
import com.example.allegrointernapp.data.Offers
import com.squareup.picasso.Picasso

class OffersAdapter(private val allOffers: Offers,
                    private val onOfferClickListener: OnOfferClickListener
): RecyclerView.Adapter<OffersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.offer_row, parent, false)

        return OffersViewHolder(row, onOfferClickListener)
    }

    override fun getItemCount(): Int {
        return allOffers.offers.size
    }

    override fun onBindViewHolder(holder: OffersViewHolder, position: Int) {

        holder.img.contentDescription = allOffers.offers[position].thumbnailUrl
        holder.title.text = allOffers.offers[position].name
        holder.price.text = allOffers.offers[position].price.amount

        Picasso.get().load(allOffers.offers[position].thumbnailUrl)
            .resize(300,300)
            .centerInside()
            .into(holder.img)
    }

    interface OnOfferClickListener{
        fun onItemClick(position: Int)
    }

}

class OffersViewHolder(view: View,
                       private val onOfferClickListener: OffersAdapter.OnOfferClickListener
): RecyclerView.ViewHolder(view), View.OnClickListener {

    val img: ImageView = view.findViewById(R.id.offerImage)
    val title: TextView = view.findViewById(R.id.offerTitle)
    val price: TextView = view.findViewById(R.id.offerPrice)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onOfferClickListener.onItemClick(adapterPosition)
    }
}

