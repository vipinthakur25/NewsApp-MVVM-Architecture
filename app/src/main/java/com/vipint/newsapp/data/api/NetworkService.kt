package com.vipint.newsapp.data.api

import com.vipint.newsapp.data.model.NewsSourcesResponse
import com.vipint.newsapp.data.model.TopHeadLineResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("top-headlines")
    suspend fun fetchTopHeadlines(
        @Query("country") country: String
    ): TopHeadLineResponse

    @GET("everything")
    suspend fun fetchSearchNews(
        @Query("q") searchQuery: String
    ): TopHeadLineResponse

    @GET("sources")
    suspend fun getNewsSources(
    ): NewsSourcesResponse


    @GET("top-headlines")
    suspend fun fetchTopHeadlinesBySources(
        @Query("sources") sources: String
    ): TopHeadLineResponse

    @GET("top-headlines")
    suspend fun fetchTopHeadlinesByLanguage(
        @Query("language") language: String
    ): TopHeadLineResponse

}