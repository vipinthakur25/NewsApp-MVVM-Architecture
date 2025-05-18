package com.vipint.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vipint.newsapp.data.local.dao.ArticleDao
import com.vipint.newsapp.data.local.entitiy.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}