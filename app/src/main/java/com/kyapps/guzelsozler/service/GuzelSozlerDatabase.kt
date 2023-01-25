package com.kyapps.guzelsozler.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kyapps.guzelsozler.model.Categories
import com.kyapps.guzelsozler.model.Quotes


@Database(entities = arrayOf(Categories::class, Quotes::class), version = 5)
abstract class GuzelSozlerDatabase : RoomDatabase(){

    abstract fun GuzelSozlerDao() : GuzelSozlerDao

    companion object{

        @Volatile private var instance : GuzelSozlerDatabase? = null

        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also{
                instance = it
            }
        }

        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,GuzelSozlerDatabase::class.java,"guzelsozlerdatabase"
        ).build()
    }
}