package com.kyapps.guzelsozler.service

import com.kyapps.guzelsozler.model.Categories
import com.kyapps.guzelsozler.model.Quotes
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GuzelSozlerAPI {

    @GET("categories.php")
    fun getCategories():Single<List<Categories>>

    @GET("quotes.php")
    fun getQuotes():Single<List<Quotes>>

    @GET("quote.php")
    fun getQuotesById(@Query("cat_id") cat_id: String):Single<List<Quotes>>

}