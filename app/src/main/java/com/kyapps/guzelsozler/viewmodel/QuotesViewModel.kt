package com.kyapps.guzelsozler.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.kyapps.guzelsozler.model.Quotes
import com.kyapps.guzelsozler.service.GuzelSozlerAPIService
import com.kyapps.guzelsozler.service.GuzelSozlerDatabase
import com.kyapps.guzelsozler.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class QuotesViewModel(application: Application): BaseViewModel(application) {

    private val guzelSozlerAPIService = GuzelSozlerAPIService()
    private val disposable = CompositeDisposable()
    private val customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val quotes = MutableLiveData<List<Quotes>>()
    val quotesError = MutableLiveData<Boolean>()
    val quotesLoading = MutableLiveData<Boolean>()


    fun refreshData(id : String){
        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite(id) //10dk dan az ise sql den al
        } else{
            getDataFromAPI(id) //10dkdan fazla ise apiden al.

        }
    }

    fun refreshFromAPI(id : String){
        getDataFromAPI(id)
    }

    private fun getDataFromSQLite(id : String){
        quotesLoading.value = true
        launch {
            val quotes = GuzelSozlerDatabase(getApplication()).GuzelSozlerDao().getQuotes(id)
            showQuotes(quotes)
            Toast.makeText(getApplication(), "Quotes from SQLite", Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI(id : String) {
        quotesLoading.value = true
        disposable.add(
            guzelSozlerAPIService.getQuoteData(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Quotes>>(){
                    override fun onSuccess(t: List<Quotes>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(), "Quotes from API", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        quotesLoading.value = false
                        quotesError.value = true
                    }

                })
        )
    }

    private fun showQuotes(quotesList: List<Quotes>){
        quotes.value = quotesList
        quotesError.value = false
        quotesLoading.value = false
    }

    private fun storeInSQLite(list: List<Quotes>){
        launch {
            val dao = GuzelSozlerDatabase(getApplication()).GuzelSozlerDao()
            dao.deleteAllQuotes()
            val listLong = dao.insertAllQuotes(*list.toTypedArray())
            var i = 0
            while (i <list.size){
                list[i].quoteId = listLong[i].toInt()
                i += 1
            }

            showQuotes(list)
        }
        customPreferences.saveTime(System.nanoTime())
    }

}