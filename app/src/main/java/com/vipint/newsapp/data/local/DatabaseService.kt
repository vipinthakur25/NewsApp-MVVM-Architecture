package com.vipint.newsapp.data.local

import com.vipint.newsapp.data.local.entitiy.Article
import kotlinx.coroutines.flow.Flow

interface DatabaseService {
    fun getArticle(): Flow<List<Article>>

    fun deleteAllInsertAll(articles: List<Article>)
}