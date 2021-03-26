package com.timaklokov.tickets.data.db

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.timaklokov.tickets.R

class SymbolRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyName = itemView.findViewById(R.id.companyName) as TextView
        val symbol = itemView.findViewById(R.id.promSymbol) as TextView
        val currentPrice = itemView.findViewById(R.id.currentUsdPrice) as TextView
        val priceChange = itemView.findViewById(R.id.priceChange) as TextView
        var isFavourite = false
        var viewHolderCardView = itemView.findViewById(R.id.viewHolderCardView) as CardView


}