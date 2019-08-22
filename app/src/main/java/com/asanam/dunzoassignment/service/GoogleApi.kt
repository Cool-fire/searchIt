package com.asanam.dunzoassignment.service

import com.asanam.dunzoassignment.service.model.SearchResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface GoogleApi {

    @GET("v1")
    suspend fun searchImages(
        @Query("key") apiKey: String,
        @Query("cx") cx: String,
        @Query("q") query: String,
        @Query("start") offset: Int
    ): Response<SearchResponse>

}