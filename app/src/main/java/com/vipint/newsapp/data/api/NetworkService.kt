package com.vipint.newsapp.data.api

import com.vipint.newsapp.data.model.NewsSourcesResponse
import com.vipint.newsapp.data.model.TopHeadLineResponse
import com.vipint.newsapp.utils.AppConstants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetworkService {

    @GET("top-headlines")
    suspend fun fetchTopHeadlines(
        @Query("country") country: String,
        @Header("X-Api-Key") apiKey: String = API_KEY
    ): TopHeadLineResponse

    @GET("everything")
    suspend fun fetchSearchNews(
        @Query("q") searchQuery: String,
        @Header("X-Api-Key") apiKey: String = API_KEY
    ): TopHeadLineResponse

    @GET("sources")
    suspend fun getNewsSources(
        @Header("X-Api-Key") apiKey: String = API_KEY
    ): NewsSourcesResponse


    @GET("top-headlines")
    suspend fun fetchTopHeadlinesBySources(
        @Query("sources") sources: String,
        @Header("X-Api-Key") apiKey: String = API_KEY
    ): TopHeadLineResponse

    @GET("top-headlines")
    suspend fun fetchTopHeadlinesByLanguage(
        @Query("language") language: String,
        @Header("X-Api-Key") apiKey: String = API_KEY
    ): TopHeadLineResponse

}