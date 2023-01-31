package com.kyapps.guzelsozler.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Quotes(
    @ColumnInfo("id")
    @PrimaryKey(false)
    @SerializedName("id")
    var quoteId : Int?,
    @ColumnInfo("quote")
    @SerializedName("quote")
    var quoteText : String?,
    @ColumnInfo("cat_id")
    @SerializedName("cat_id")
    var quoteCatId: Int?
)
