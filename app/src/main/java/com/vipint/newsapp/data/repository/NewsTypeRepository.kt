package com.vipint.newsapp.data.repository

import com.vipint.newsapp.utils.NewsType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsTypeRepository @Inject constructor() {

    fun getNewsType(): List<NewsType> {
        return NewsType.entries
    }
}