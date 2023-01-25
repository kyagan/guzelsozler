package com.kyapps.guzelsozler.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kyapps.guzelsozler.R
import com.kyapps.guzelsozler.adapter.QuotesAdapter
import com.kyapps.guzelsozler.databinding.FragmentQuotesBinding
import com.kyapps.guzelsozler.viewmodel.QuotesViewModel


class QuotesFragment : Fragment() {
    private lateinit var fragmentBinding : FragmentQuotesBinding
    private lateinit var viewModel : QuotesViewModel
    private val quotesAdapter = QuotesAdapter(arrayListOf())
    private var sentCategoryId = 0
    private var sentCategoryName = "Kategori Adı"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_quotes, container, false)
        return fragmentBinding.root
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            sentCategoryId = QuotesFragmentArgs.fromBundle(it).sentCategoryId
            sentCategoryName = QuotesFragmentArgs.fromBundle(it).sentCategoryName
            fragmentBinding.categoryTitle.text = sentCategoryName
        }

        viewModel = ViewModelProvider(this)[QuotesViewModel::class.java]
        viewModel.refreshFromAPI(sentCategoryId.toString())

        view.findViewById<RecyclerView>(R.id.quotesRV).layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.quotesRV).adapter = quotesAdapter
        view.findViewById<SwipeRefreshLayout>(R.id.swipeQuotes).setOnRefreshListener {
            view.findViewById<RecyclerView>(R.id.quotesRV).visibility = View.GONE
            view.findViewById<TextView>(R.id.quotesError).visibility = View.GONE
            viewModel.refreshData(sentCategoryId.toString())
            view.findViewById<ProgressBar>(R.id.quotesLoading).visibility = View.GONE
            view.findViewById<SwipeRefreshLayout>(R.id.swipeQuotes).isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.quotes.observe(viewLifecycleOwner, Observer {quotes ->
            quotes?.let{
                view?.findViewById<RecyclerView>(R.id.quotesRV)?.visibility  = View.VISIBLE
                quotesAdapter.updateQuotesList(quotes)
            }
        })

        viewModel.quotesError.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if (it){
                    view?.findViewById<TextView>(R.id.quotesError)?.visibility = View.VISIBLE
                }else{
                    view?.findViewById<TextView>(R.id.quotesError)?.visibility = View.GONE
                }
            }
        })

        viewModel.quotesLoading.observe(viewLifecycleOwner, Observer {loading ->
            loading?.let {
                if (it){
                    view?.findViewById<ProgressBar>(R.id.quotesLoading)?.visibility = View.VISIBLE
                    view?.findViewById<RecyclerView>(R.id.quotesRV)?.visibility = View.GONE
                    view?.findViewById<TextView>(R.id.quotesError)?.visibility = View.GONE
                }else{
                    view?.findViewById<ProgressBar>(R.id.quotesLoading)?.visibility = View.GONE
                }
            }
        })
    }
}