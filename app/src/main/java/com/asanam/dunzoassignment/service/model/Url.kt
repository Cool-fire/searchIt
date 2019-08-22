package com.asanam.dunzoassignment.service.model

import com.google.gson.annotations.SerializedName

data class Url (

    @SerializedName("type") val type : String,
    @SerializedName("template") val template : String
)