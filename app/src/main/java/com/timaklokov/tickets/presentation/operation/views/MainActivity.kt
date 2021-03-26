package com.timaklokov.tickets.presentation.operation.views

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.*
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import androidx.recyclerview.widget.RecyclerView
import com.timaklokov.tickets.R
import com.timaklokov.tickets.presentation.ViewModels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var searchWidget: SearchView
    lateinit var allSymbols: TextView
    lateinit var favourite: TextView
    lateinit var fragmentContainer: FragmentContainerView
    lateinit var fragment: BaseFragment

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        fragmentContainer = findViewById(R.id.fragment_container)
        fragment = ListFragment()
        supportFragmentManager.beginTransaction().setTransition(TRANSIT_FRAGMENT_OPEN).add(R.id.fragment_container, fragment).commit()
        searchWidget = findViewById(R.id.search_view)
        searchWidget.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                fragment.adapter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                fragment.adapter.filter(newText)
                return true
            }
        })
//
//
//
        allSymbols = findViewById(R.id.all_symbols)
        favourite = findViewById(R.id.fav)
        allSymbols.isEnabled = false
        allSymbols.setOnClickListener {
            allSymbols.isEnabled = false
            favourite.isEnabled = true
            allSymbols.setTextColor(getColor(R.color.background))
            favourite.setTextColor(getColor(R.color.white))
            searchWidget.setQuery("", false)
            fragment = ListFragment()
            supportFragmentManager.beginTransaction().setTransition(TRANSIT_FRAGMENT_OPEN).replace(R.id.fragment_container, fragment).commit()
        }
        favourite.setOnClickListener {
            allSymbols.isEnabled = true
            favourite.isEnabled = false
            allSymbols.setTextColor(getColor(R.color.white))
            favourite.setTextColor(getColor(R.color.background))
            searchWidget.setQuery("", false)
            fragment = FavouriteFragment()
            supportFragmentManager.beginTransaction().setTransition(TRANSIT_FRAGMENT_OPEN).replace(R.id.fragment_container, fragment).commit()
        }


    }
}


