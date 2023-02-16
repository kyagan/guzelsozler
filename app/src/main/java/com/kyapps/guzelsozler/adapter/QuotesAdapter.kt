package com.kyapps.guzelsozler.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kyapps.guzelsozler.R
import com.kyapps.guzelsozler.databinding.ItemQuotesBinding
import com.kyapps.guzelsozler.model.Quotes
import com.kyapps.guzelsozler.service.GuzelSozlerDatabase


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
        val clipboard = v.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("quote", v.findViewById<TextView>(R.id.item_quotetext).text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(v.context,v.findViewById<TextView>(R.id.item_quotetext).text,Toast.LENGTH_SHORT).show()
    }
}