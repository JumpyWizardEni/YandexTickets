package com.timaklokov.tickets.data.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.timaklokov.tickets.R
import com.timaklokov.tickets.data.db.SymbolRecyclerViewHolder
import com.timaklokov.tickets.data.db.models.SymbolEntity
import com.timaklokov.tickets.presentation.ViewModels.MainViewModel
import com.timaklokov.tickets.presentation.operation.views.SymbolActivity
import java.util.*
import kotlin.collections.ArrayList

class SymbolRecyclerAdapter(var context: Context, val mainViewModel: MainViewModel) :
    RecyclerView.Adapter<SymbolRecyclerViewHolder>() {
    private var items: MutableList<SymbolEntity> = ArrayList()
    private var itemsCopy: MutableList<SymbolEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymbolRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.forex_recycle_view_item,
                parent, false
            )

        return SymbolRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: SymbolRecyclerViewHolder, position: Int) {
        holder.companyName.text = items[position].companyName
        holder.companyName.isSelected = true

        holder.symbol.text = items[position].symbol
        holder.currentPrice.text = items[position].price.toString() + "$"
        if (items[position].change >= 0.0) {
            holder.priceChange.setTextColor(context.getColor(R.color.up))
        } else {
            holder.priceChange.setTextColor(context.getColor(R.color.down))
        }
        if (items[position].isFavourite) {
            holder.symbol.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_baseline_favorite_24,
                0
            )
        } else {
            holder.symbol.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_baseline_favorite_border_24,
                0
            )
        }
        holder.priceChange.text = items[position].change.toString() + "$"
        holder.symbol.setOnClickListener {
            mainViewModel.updateGroupBySymbol(
                items[position].symbol,
                !items[position].isFavourite
            )
        }
        holder.viewHolderCardView.setOnClickListener {
            var intent = Intent(context, SymbolActivity::class.java)
            intent.putExtra("symbol", items[position].symbol)
            intent.putExtra("company", items[position].companyName)
            context.startActivity(intent)
        }
    }

    fun setData(newData: MutableList<SymbolEntity>) {
        items = newData
        itemsCopy.clear()
        itemsCopy.addAll(newData)
        notifyDataSetChanged()
    }

    fun setData() {
        items.clear()
        items.addAll(itemsCopy)
        notifyDataSetChanged()
    }

    fun setFavourite() {
        items.clear()
        for (item in itemsCopy) {
            if (item.isFavourite == true) {
                items.add(item)
            }
        }
        itemsCopy.clear()
        itemsCopy.addAll(items)
        notifyDataSetChanged()

    }

    fun filter(text: String) {
        items.clear()
        if (text.isEmpty()) {
            items.addAll(itemsCopy)
        } else {
            for (item in itemsCopy) {
                if (item.companyName.toLowerCase()
                        .contains(text.toLowerCase()) || item.symbol.toLowerCase()
                        .contains(text.toLowerCase())
                ) {
                    items.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }


}