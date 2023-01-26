package com.kyapps.guzelsozler.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kyapps.guzelsozler.R
import com.kyapps.guzelsozler.databinding.ItemQuotesBinding
import com.kyapps.guzelsozler.model.Quotes
import com.kyapps.guzelsozler.view.QuotesFragment
import kotlinx.coroutines.withContext


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
        holder.view.imgFavorite.setOnClickListener{
            holder.view.imgFavorite.setBackgroundResource(R.drawable.copy)
        }

    }

    fun updateQuotesList(newQuotesList: List<Quotes>){
        quotesList.clear()
        quotesList.addAll(newQuotesList)
        notifyDataSetChanged()
    }

    fun AddFavorites(){


    }

    override fun onQuotesClicked(v: View) {
        /*holder.view.findViewById<ImageButton>(R.id.imgFav).setOnClickListener{
            v.findViewById<ImageButton>(R.id.imgFav).background
            Toast.makeText(holder.view.context,"dd",Toast.LENGTH_SHORT).show()
        }*/
        Toast.makeText(v.context,"Quote clicked no position",Toast.LENGTH_SHORT).show()
    }
}