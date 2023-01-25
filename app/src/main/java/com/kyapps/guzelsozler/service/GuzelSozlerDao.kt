package com.kyapps.guzelsozler.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kyapps.guzelsozler.model.Categories
import com.kyapps.guzelsozler.model.Quotes

@Dao
interface GuzelSozlerDao {

    @Insert
    suspend fun insertAll(vararg categories: Categories) : List<Long>

    @Insert
    suspend fun insertAllQuotes(vararg quotes: Quotes) : List<Long>

    @Query("SELECT * FROM categories")
    suspend fun getCategories(): List<Categories>

    @Query("SELECT * FROM quotes")
    suspend fun getQuotes(): List<Quotes>

    //@Query("SELECT * FROM quotes")
    //suspend fun getQuotesByCatId(): List<Quotes>

    //@Query("SELECT * FROM Categories WHERE categoryId = : categoryId")
    //suspend fun getCategory(categoryId : Int): Categories

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()

    @Query("DELETE FROM quotes")
    suspend fun deleteAllQuotes()
}