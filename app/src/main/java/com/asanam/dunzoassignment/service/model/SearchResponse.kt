package com.asanam.dunzoassignment.service.model

import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @SerializedName("kind") val kind : String? = null,
    @SerializedName("url") val url : Url? = null,
    @SerializedName("queries") val queries : Queries? = null,
    @SerializedName("context") val context : Context? = null,
    @SerializedName("searchInformation") val searchInformation : SearchInformation? = null,
    @SerializedName("spelling") val spelling : Spelling? = null,
    @SerializedName("items") val items : List<Items>? = null
)
