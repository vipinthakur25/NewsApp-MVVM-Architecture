package com.vipint.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.vipint.newsapp.data.local.entitiy.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert
    fun insertAllArticle(articles: List<Article>)

    @Query("SELECT * FROM article")
    fun getAll(): Flow<List<Article>>

    @Query("DELETE FROM article")
    fun deleteALl()

    @Transaction
    fun deleteAllAndInsertAll(articles: List<Article>) {
        deleteALl()
        insertAllArticle(articles)
    }
}