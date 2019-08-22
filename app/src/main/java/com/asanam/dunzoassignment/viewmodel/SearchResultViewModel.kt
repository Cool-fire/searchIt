package com.asanam.dunzoassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asanam.dunzoassignment.service.ApiFactory
import com.asanam.dunzoassignment.service.model.Items
import com.asanam.dunzoassignment.service.model.SearchResponse
import com.asanam.dunzoassignment.service.repository.SearchResultRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchResultViewModel : ViewModel() {

    private lateinit var searchResponse: SearchResponse
    private var imagesResponse: MutableList<Items>? = ArrayList()
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)


    private val repository : SearchResultRepository= SearchResultRepository(ApiFactory.googleApi)

    val imageResponseLiveData = MutableLiveData<MutableList<Items>>()
    val isError : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun fetchImages(query: String, offset: Int) {
        scope.launch {
            if (offset != -1) {
                repository.fetchImages(query, offset)?.let {
                    searchResponse = it
                }
                imagesResponse = repository.fetchImages(query, offset)?.items?.toMutableList()
                if (imagesResponse == null) {
                    isError.postValue(true)
                } else {
                    isError.postValue(false)
                }
                imageResponseLiveData.postValue(imagesResponse)
            }
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()

    fun getNextPageOffset(): Int? {
        return if (searchResponse.queries == null || searchResponse.queries?.nextPage == null
        ) {
            -1
        } else searchResponse.queries?.nextPage?.get(0)?.startIndex
    }

}