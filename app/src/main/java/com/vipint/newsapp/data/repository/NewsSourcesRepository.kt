package com.vipint.newsapp.data.repository

import com.vipint.newsapp.data.api.NetworkService
import com.vipint.newsapp.data.model.SourcesItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsSourcesRepository @Inject constructor(private val networkService: NetworkService) {
    fun fetchNewsSources(): Flow<List<SourcesItem>?> {
        return flow {
            emit(networkService.getNewsSources())
        }.map {
            it.sources
        }
    }
}