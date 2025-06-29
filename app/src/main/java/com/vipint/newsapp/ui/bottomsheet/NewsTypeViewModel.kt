package com.vipint.newsapp.ui.bottomsheet

import androidx.lifecycle.ViewModel
import com.vipint.newsapp.data.repository.NewsTypeRepository
import com.vipint.newsapp.utils.NewsType
import javax.inject.Inject

class NewsTypeViewModel @Inject constructor(private val newsTypeRepository: NewsTypeRepository) :
    ViewModel() {

    fun fetchNewsType(): List<NewsType> {
        return newsTypeRepository.getNewsType()
    }
}