package com.kyapps.guzelsozler.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Categories(
    @ColumnInfo("id")
    @PrimaryKey(false)
    @SerializedName("id")
    var categoryId: Int?,
    @ColumnInfo("name")
    @SerializedName("name")
    val categoryName: String?,
    @ColumnInfo("icon")
    @SerializedName("icon")
    val categoryIcon: String?

)