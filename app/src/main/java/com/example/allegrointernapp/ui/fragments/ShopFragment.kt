package com.example.allegrointernapp.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allegrointernapp.R
import com.example.allegrointernapp.adapters.OffersAdapter
import com.example.allegrointernapp.data.data_model.Offer
import com.example.allegrointernapp.viewmodels.ShopViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_shop.*


class ShopFragment : Fragment(), OffersAdapter.OnOfferClickListener{

    private lateinit var shopViewModel: ShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

       shopViewModel = ViewModelProvider(requireActivity()).get(ShopViewModel(requireActivity().application)::class.java)
        Log.e("ViewModel", shopViewModel.hashCode().toString() + ":ShopFragment")

        val noInternetSbar = Snackbar.make(
            swipeLayoutFrag,
            "Check your internet connection and pull to refresh",
            Snackbar.LENGTH_INDEFINITE)

        val noDataSnackbar =  Snackbar.make(
            swipeLayoutFrag,
            "No data to show",
            Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
                if(isOnline()) shopViewModel.refreshData()
                else noInternetSbar.show()
            }


        shopViewModel.getAllOffersLiveData().observe(viewLifecycleOwner, Observer {
                if(it.isNullOrEmpty()){
                    swipeLayoutFrag.isRefreshing = false
                    noDataSnackbar.show()

                }else {
                    recyclerViewFrag.layoutManager = LinearLayoutManager(requireContext())
                    val adapter = OffersAdapter(it, this)
                    recyclerViewFrag.adapter = adapter
                    swipeLayoutFrag.isRefreshing = false
                    noDataSnackbar.dismiss()
                }
        })

        swipeLayoutFrag.setOnRefreshListener {
            if(isOnline()) {
                shopViewModel.refreshData()
                noInternetSbar.dismiss()
            }
            else {
                swipeLayoutFrag.isRefreshing = false
                noDataSnackbar.show()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_shop, menu)
        val item = menu.findItem(R.id.search)
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        val searchView = item.actionView as SearchView

        //Check user input, after every change update recyclerView with matched results
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean { return false }

            override fun onQueryTextChange(newText: String): Boolean {
                val listOfMatches = ArrayList<Offer>()
                shopViewModel.getAllOffersLiveData().value?.forEach {
                    if(it.name.contains(newText, true)){
                        listOfMatches.add(it)
                    }
                }
                val list = listOfMatches.toList()
                recyclerViewFrag.adapter = OffersAdapter(list, this@ShopFragment)
                return true
            }
        })
    }

    override fun onItemClick(position: Int, id: String) {
        val offers = shopViewModel.getAllOffersLiveData().value!!
        for(offer in offers){
               if(offer.id == id){
                   shopViewModel.setSelectedOffer(offer)
               }
           }
        val detailFragment =
            DetailFragment()
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, detailFragment, "DetailFragment")
            addToBackStack(null)
            commit()
        }
    }

    private fun isOnline(): Boolean{
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}
