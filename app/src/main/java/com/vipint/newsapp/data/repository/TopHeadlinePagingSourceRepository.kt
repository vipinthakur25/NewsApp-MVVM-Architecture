package com.vipint.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vipint.newsapp.data.api.NetworkService
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.data.paging.TopHeadlinePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlinePagingSourceRepository @Inject constructor(private val networkService: NetworkService) {

    fun getPaginatedTopHeadline(): Flow<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                TopHeadlinePagingSource(networkService)
            }
        ).flow
    }
}