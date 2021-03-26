package com.timaklokov.tickets.presentation.operation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.timaklokov.tickets.R
import com.timaklokov.tickets.data.adapters.SymbolRecyclerAdapter
import com.timaklokov.tickets.presentation.ViewModels.MainViewModel
import androidx.lifecycle.Observer


class FavouriteFragment : BaseFragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_list, container, false)

        mainViewModel = MainViewModel(view.context)
        adapter = SymbolRecyclerAdapter(view.context, mainViewModel)
        groupRecyclerView = view.findViewById(R.id.group_recycler_view)
        groupRecyclerView.adapter = adapter
        groupRecyclerView.layoutManager = LinearLayoutManager(view.context)
        swipeLayout = view.findViewById(R.id.swipe_container)
        swipeLayout.setOnRefreshListener {
            mainViewModel.updateData()

        }
        mainViewModel.getData().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setData(it.toMutableList())
                adapter.setFavourite()
                swipeLayout.setRefreshing(false)

            }
        })
        return view
    }


}