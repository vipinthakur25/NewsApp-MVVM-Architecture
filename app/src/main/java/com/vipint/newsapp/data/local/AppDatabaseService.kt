package com.vipint.newsapp.data.local

import com.vipint.newsapp.data.local.entitiy.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppDatabaseService @Inject constructor(private val appDatabase: AppDatabase) :
    DatabaseService {
    override fun getArticle(): Flow<List<Article>> {
        return appDatabase.articleDao().getAll()
    }

    override fun deleteAllInsertAll(articles: List<Article>) {
        return appDatabase.articleDao().deleteAllAndInsertAll(articles)
    }
}