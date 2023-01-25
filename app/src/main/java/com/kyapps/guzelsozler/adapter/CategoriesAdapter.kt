package com.kyapps.guzelsozler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kyapps.guzelsozler.R
import com.kyapps.guzelsozler.databinding.ItemCategoriesBinding
import com.kyapps.guzelsozler.model.Categories
import com.kyapps.guzelsozler.view.CategoriesFragmentDirections

class CategoriesAdapter(private val categoriesList: ArrayList<Categories>):
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>(),CategoriesClickListener {

    class CategoriesViewHolder(var view: ItemCategoriesBinding) : RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCategoriesBinding>(inflater,R.layout.item_categories,parent,false)
        return CategoriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.view.category = categoriesList[position] //data binding
        holder.view.listener = this
        /*
        val itemCategoryName = holder.view.findViewById<TextView>(R.id.item_category_name)
        itemCategoryName.text = categoriesList[position].categoryName

        val itemCategoryIcon = holder.view.findViewById<ImageView>(R.id.item_category_icon)
        itemCategoryIcon.downloadFromUrl(categoriesList[position].categoryIcon, placeholderProgressBar(holder.view.context))
         */
    }

    fun updateCategoriesList(newCategoriesList: List<Categories>){
        categoriesList.clear()
        categoriesList.addAll(newCategoriesList)
        notifyDataSetChanged()
    }

    override fun onCategoriesClicked(v: View) {
        val sentCategoryId = v.findViewById<TextView>(R.id.item_category_id).text.toString().toInt()
        val sentCategoryName = v.findViewById<TextView>(R.id.item_category_name).text.toString()
        val action = CategoriesFragmentDirections.actionCategoriesFragmentToQuotesFragment(sentCategoryId,sentCategoryName)
        Navigation.findNavController(v).navigate(action)
    }

}