package com.example.calinews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsSource(
    @Expose var id:String,
    @SerializedName("name") @Expose var name:String
)