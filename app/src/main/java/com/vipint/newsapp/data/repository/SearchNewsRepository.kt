package com.vipint.newsapp.data.repository

import com.vipint.newsapp.data.api.NetworkService
import com.vipint.newsapp.data.model.ArticlesItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchNewsRepository @Inject constructor(private val networkService: NetworkService)  {

    suspend fun getSearchNews(searchQuery: String) = networkService.fetchSearchNews(searchQuery)
}