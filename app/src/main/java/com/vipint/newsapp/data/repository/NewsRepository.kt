package com.vipint.newsapp.data.repository

import com.vipint.newsapp.data.api.NetworkService
import com.vipint.newsapp.data.model.ArticlesItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val networkService: NetworkService) {
    fun getNewsBySources(sources: String): Flow<List<ArticlesItem>?> {
        return flow {
            emit(networkService.fetchTopHeadlinesBySources(sources = sources))
        }.map {
            it.articles
        }
    }

    fun getNewsByLanguage(language: String): Flow<List<ArticlesItem>?> {
        return flow {
            emit(networkService.fetchTopHeadlinesByLanguage(language))
        }.map {
            it.articles
        }
    }

    fun getNewsByCountry(country: String): Flow<List<ArticlesItem>?> {
        return flow {
            emit(networkService.fetchTopHeadlines(country))
        }.map {
            it.articles
        }
    }
}