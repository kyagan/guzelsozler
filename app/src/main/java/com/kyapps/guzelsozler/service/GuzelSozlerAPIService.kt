package com.kyapps.guzelsozler.service

import com.kyapps.guzelsozler.model.Categories
import com.kyapps.guzelsozler.model.Quotes
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GuzelSozlerAPIService {

    private val BASE_URL = "https://kaanyagan.com/and/guzelsozler/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(GuzelSozlerAPI::class.java)

    fun getData() : Single<List<Categories>>{
        return api.getCategories()
    }

    fun getQuotes() : Single<List<Quotes>>{
        return api.getQuotes()
    }

    fun getQuoteData(cat_id : String) : Single<List<Quotes>>{
        return api.getQuotesById(cat_id)
    }
}