package com.kyapps.guzelsozler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.contentValuesOf
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kyapps.guzelsozler.R
import com.kyapps.guzelsozler.databinding.ItemQuotesBinding
import com.kyapps.guzelsozler.model.Categories
import com.kyapps.guzelsozler.model.Quotes

class QuotesAdapter(val quotesList : ArrayList<Quotes>): RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder>(), QuotesClickListener {

    class QuotesViewHolder(var view: ItemQuotesBinding) : RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemQuotesBinding>(inflater,R.layout.item_quotes,parent,false)
        return QuotesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quotesList.size
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.view.quote = quotesList[position]
        holder.view.listener = this


    }

    fun updateQuotesList(newQuotesList: List<Quotes>){
        quotesList.clear()
        quotesList.addAll(newQuotesList)
        notifyDataSetChanged()
    }

    override fun onQuotesClicked(v: View) {

    }
}