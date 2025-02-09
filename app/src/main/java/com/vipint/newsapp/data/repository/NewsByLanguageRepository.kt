package com.vipint.newsapp.data.repository

import com.vipint.newsapp.data.api.NetworkService
import com.vipint.newsapp.data.model.ArticlesItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsByLanguageRepository @Inject constructor(private val service: NetworkService) {

    fun getNewsByLanguage(language: String): Flow<List<ArticlesItem>?> {
        return flow {
            emit(service.fetchTopHeadlinesByLanguage(language))
        }.map {
            it.articles
        }
    }
}

