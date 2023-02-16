package com.kyapps.guzelsozler.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kyapps.guzelsozler.model.Categories
import com.kyapps.guzelsozler.service.GuzelSozlerAPIService
import com.kyapps.guzelsozler.service.GuzelSozlerDao
import com.kyapps.guzelsozler.service.GuzelSozlerDatabase
import com.kyapps.guzelsozler.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CategoriesViewModel(application: Application) : BaseViewModel(application) {

    private val guzelSozlerAPIService = GuzelSozlerAPIService()
    private val disposable = CompositeDisposable()
    private val customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val categories = MutableLiveData<List<Categories>>()
    val categoriesError = MutableLiveData<Boolean>()
    val categoriesLoading = MutableLiveData<Boolean>()

    fun refreshCategoriesData(){
        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getCategoriesDataFromSQLite() //10dk dan az ise sql den al
        } else{
            getCategoriesDataFromAPI() //10dkdan fazla ise apiden al.

        }
    }

    fun refreshCategoriesDataFromAPI(){
        getCategoriesDataFromAPI()
    }

    private fun getCategoriesDataFromSQLite(){
        categoriesLoading.value = true
        launch {
            val categories = GuzelSozlerDatabase(getApplication()).GuzelSozlerDao().getCategories()
            showCategories(categories)
            //Toast.makeText(getApplication(), "Categories from SQLite", Toast.LENGTH_LONG).show()
        }
    }

    private fun getCategoriesDataFromAPI() {
        categoriesLoading.value = true
        disposable.add(
            guzelSozlerAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Categories>>(){
                    override fun onSuccess(t: List<Categories>) {
                        storeCategoriesInSQLite(t)
                        //Toast.makeText(getApplication(), "Categories from API", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        categoriesLoading.value = false
                        categoriesError.value = true
                    }

                })
        )
    }

    private fun showCategories(categoriesList: List<Categories>){
        categories.value = categoriesList
        categoriesError.value = false
        categoriesLoading.value = false
    }

    private fun storeCategoriesInSQLite(list: List<Categories>){
        launch {
            val dao = GuzelSozlerDatabase(getApplication()).GuzelSozlerDao()
            dao.deleteAllCategories()
            val listLong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i <list.size){
                list[i].categoryId = listLong[i].toInt()
                i += 1
            }

            showCategories(list)
        }
        customPreferences.saveTime(System.nanoTime())
    }


}