package com.vipint.newsapp.data.repository

import com.vipint.newsapp.data.api.NetworkService
import com.vipint.newsapp.data.model.ArticlesItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlinesRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadline(country: String): Flow<List<ArticlesItem>?> {
        return flow {
            emit(networkService.fetchTopHeadlines(country))
        }.map {
            it.articles
        }
    }
}