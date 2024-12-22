package com.vipint.newsapp.data.repository

import com.vipint.newsapp.data.api.NetworkService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchNewsRepository @Inject constructor(private val networkService: NetworkService)  {

    suspend fun getSearchNews(searchQuery: String) = networkService.fetchSearchNews(searchQuery)
}