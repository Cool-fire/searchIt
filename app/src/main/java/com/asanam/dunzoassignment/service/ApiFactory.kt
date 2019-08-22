package com.asanam.dunzoassignment.service

import com.asanam.dunzoassignment.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    fun retrofit() : Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val googleApi : GoogleApi = retrofit().create(GoogleApi::class.java)

}