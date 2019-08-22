package com.asanam.dunzoassignment.service.model

import com.google.gson.annotations.SerializedName

data class Spelling (

	@SerializedName("correctedQuery") val correctedQuery : String,
	@SerializedName("htmlCorrectedQuery") val htmlCorrectedQuery : String
)