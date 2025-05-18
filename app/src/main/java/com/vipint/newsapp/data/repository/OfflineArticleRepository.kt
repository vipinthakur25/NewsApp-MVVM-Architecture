package com.vipint.newsapp.data.repository

import com.vipint.newsapp.data.api.NetworkService
import com.vipint.newsapp.data.local.AppDatabaseService
import com.vipint.newsapp.data.local.entitiy.Article
import com.vipint.newsapp.data.model.toArticleEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineArticleRepository @Inject constructor(
    private val databaseService: AppDatabaseService,
    private val networkService: NetworkService
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getArticles(): Flow<List<Article>> {
        return flow { emit(networkService.fetchTopHeadlines("us")) }.map {
            it.articles.map { apiArticle ->
                apiArticle.toArticleEntity()
            }
        }.flatMapConcat { articles ->
            flow {
                emit(databaseService.deleteAllInsertAll(articles))
            }
        }.flatMapConcat {
            databaseService.getArticle()
        }
    }

    fun getArticlesDirectlyFromDb(): Flow<List<Article>> {
        return databaseService.getArticle()
    }
}

