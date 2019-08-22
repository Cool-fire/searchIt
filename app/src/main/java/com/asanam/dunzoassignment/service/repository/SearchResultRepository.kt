package com.asanam.dunzoassignment.service.repository

import com.asanam.dunzoassignment.BuildConfig
import com.asanam.dunzoassignment.service.GoogleApi
import com.asanam.dunzoassignment.service.model.SearchResponse

class SearchResultRepository(private val googleApi: GoogleApi) : BaseRepository() {

    suspend fun fetchImages(searchString: String, offset: Int) : SearchResponse? {
        return safeApiCall(
        call = { googleApi.searchImages(BuildConfig.API_KEY, BuildConfig.CX_ID, searchString, offset)},
        errorMessage = "Error occurred")
    }
}