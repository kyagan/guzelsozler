package com.kyapps.guzelsozler.view

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kyapps.guzelsozler.R
import com.kyapps.guzelsozler.adapter.CategoriesAdapter
import com.kyapps.guzelsozler.databinding.FragmentCategoriesBinding
import com.kyapps.guzelsozler.model.Quotes
import com.kyapps.guzelsozler.viewmodel.CategoriesViewModel

class CategoriesFragment : Fragment() {

    private lateinit var fragmentBinding : FragmentCategoriesBinding
    private lateinit var viewModel: CategoriesViewModel
    private val categoriesAdapter = CategoriesAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)
        return fragmentBinding.root
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
        viewModel.refreshCategoriesData()

        view.findViewById<RecyclerView>(R.id.categoriesRV).layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.categoriesRV).adapter = categoriesAdapter

        view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout).setOnRefreshListener {
            view.findViewById<RecyclerView>(R.id.categoriesRV).visibility = View.GONE
            view.findViewById<TextView>(R.id.categoriesError).visibility = View.GONE
            viewModel.refreshCategoriesData()
            view.findViewById<ProgressBar>(R.id.categoriesLoading).visibility = View.GONE
            view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout).isRefreshing = false
        }
        observeLiveData()

        //image menu button clicked
        view.findViewById<ImageButton>(R.id.imgSettings).setOnClickListener{
            Toast.makeText(context,getText(R.string.settings),Toast.LENGTH_SHORT).show()
        }

        //img favs button clicked
        view.findViewById<ImageButton>(R.id.imgShare).setOnClickListener{
            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"Güzel Sözler uygulamasını sen de indir! https://play.google.com/store/apps/details?id=com.kyapps.guzelsozler:")
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Paylaş:"))
        }


    }

    private fun observeLiveData(){
        viewModel.categories.observe(viewLifecycleOwner, Observer {categories ->
            categories?.let{
                view?.findViewById<RecyclerView>(R.id.categoriesRV)?.visibility  = View.VISIBLE
                categoriesAdapter.updateCategoriesList(categories)
            }
        })

        viewModel.categoriesError.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if (it){
                    view?.findViewById<TextView>(R.id.categoriesError)?.visibility = View.VISIBLE
                }else{
                    view?.findViewById<TextView>(R.id.categoriesError)?.visibility = View.GONE
                }
            }
        })

        viewModel.categoriesLoading.observe(viewLifecycleOwner, Observer {loading ->
            loading?.let {
                if (it){
                    view?.findViewById<ProgressBar>(R.id.categoriesLoading)?.visibility = View.VISIBLE
                    view?.findViewById<RecyclerView>(R.id.categoriesRV)?.visibility = View.GONE
                    view?.findViewById<TextView>(R.id.categoriesError)?.visibility = View.GONE
                }else{
                    view?.findViewById<ProgressBar>(R.id.categoriesLoading)?.visibility = View.GONE
                }
            }
        })
    }
}
