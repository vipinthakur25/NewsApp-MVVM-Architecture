package com.vipint.newsapp.data.api

import com.vipint.newsapp.data.model.TopHeadLineResponse
import com.vipint.newsapp.utils.AppConstant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetworkService {

    @GET("top-headlines")
    suspend fun fetchTopHeadlines(
        @Query("country") country: String,
        @Header("X-Api-Key") apiKey : String = API_KEY
    ): TopHeadLineResponse

}