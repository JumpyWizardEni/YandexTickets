package com.timaklokov.tickets.presentation.operation.views

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.timaklokov.tickets.data.adapters.SymbolRecyclerAdapter
import com.timaklokov.tickets.presentation.ViewModels.MainViewModel

abstract class BaseFragment: Fragment() {
    lateinit var adapter: SymbolRecyclerAdapter
    lateinit var mainViewModel: MainViewModel
    lateinit var groupRecyclerView: RecyclerView
    lateinit var swipeLayout: SwipeRefreshLayout
}