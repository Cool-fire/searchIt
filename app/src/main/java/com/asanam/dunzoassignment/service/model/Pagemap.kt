package com.asanam.dunzoassignment.service.model

import com.google.gson.annotations.SerializedName

data class Pagemap (

	@SerializedName("cse_thumbnail") val cse_thumbnail : List<Cse_thumbnail>? = null,
	@SerializedName("cse_image") val cse_image : List<Cse_image>,
	@SerializedName("metatags") val metatags : List<Metatags>
)