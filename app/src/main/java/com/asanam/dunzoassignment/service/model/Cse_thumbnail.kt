package com.asanam.dunzoassignment.service.model

import com.google.gson.annotations.SerializedName

data class Cse_thumbnail (

	@SerializedName("width") val width : Int,
	@SerializedName("height") val height : Int,
	@SerializedName("src") val src : String
)