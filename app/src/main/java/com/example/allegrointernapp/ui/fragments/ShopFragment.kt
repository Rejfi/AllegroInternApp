package com.example.allegrointernapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allegrointernapp.R
import com.example.allegrointernapp.adapters.OffersAdapter
import com.example.allegrointernapp.data.data_model.Offer
import com.example.allegrointernapp.network.NetworkChecker
import com.example.allegrointernapp.viewmodels.ShopViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_shop.*


class ShopFragment : Fragment(), OffersAdapter.OnOfferClickListener{

    private lateinit var shopViewModel: ShopViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

       shopViewModel = ViewModelProvider(requireActivity()).get(ShopViewModel(requireActivity().application)::class.java)

        val noInternetSnackbar = Snackbar.make(
            swipeLayoutFrag,
            "Check your internet connection and pull to refresh",
            Snackbar.LENGTH_INDEFINITE)

        val noDataSnackbar =  Snackbar.make(
            swipeLayoutFrag,
            "No data to show",
            Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
                if(NetworkChecker.isOnline(requireContext())) shopViewModel.refreshData()
                else noInternetSnackbar.show()
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

        /**
         * Pull to refresh data if network is available
         */
        swipeLayoutFrag.setOnRefreshListener {
            if(NetworkChecker.isOnline(requireContext())) {
                shopViewModel.refreshData()
                noInternetSnackbar.dismiss()
            }
            else {
                swipeLayoutFrag.isRefreshing = false
                noDataSnackbar.show()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_shop, menu)
        val item = menu.findItem(R.id.search)
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        val searchView = item.actionView as SearchView
        searchView.queryHint = "What do you wish?"

        /**
         * Check user input, after every change update recyclerView with matched results
         */
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val listOfMatches = ArrayList<Offer>()
                shopViewModel.getAllOffersLiveData().value?.forEach {
                    if(it.name.contains(query, true)){
                        listOfMatches.add(it)
                    }
                }
                val list = listOfMatches.toList().sortedBy { it.price.amount.toDouble() }
                recyclerViewFrag.adapter = OffersAdapter(list, this@ShopFragment)
                //searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val listOfMatches = ArrayList<Offer>()
                shopViewModel.getAllOffersLiveData().value?.forEach {
                    if(it.name.contains(newText, true)){
                        listOfMatches.add(it)
                    }
                }
                val list = listOfMatches.toList().sortedBy { it.price.amount.toDouble() }
                recyclerViewFrag.adapter = OffersAdapter(list, this@ShopFragment)
                return true
            }
        })

    }

    /**
     * Click to open DetailFragment with details of offer
     * @param position -> Position in the recyclerView
     * @param id -> String contains offer ID
     */
    override fun onItemClick(position: Int, id: String) {
        val offers = shopViewModel.getAllOffersLiveData().value!!
        for(offer in offers){
               if(offer.id == id){ shopViewModel.setSelectedOffer(offer) }
           }
        val detailFragment = DetailFragment()
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, detailFragment, "DetailFragment")
            addToBackStack(null)
            commit()
        }
    }
}
