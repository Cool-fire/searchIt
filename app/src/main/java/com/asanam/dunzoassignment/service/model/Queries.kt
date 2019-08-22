package com.asanam.dunzoassignment.service.model

import com.google.gson.annotations.SerializedName

data class Queries (

	@SerializedName("request") val request : List<Request>,
	@SerializedName("nextPage") val nextPage : List<NextPage>

)