package com.vipint.newsapp.ui.bottomsheet

import androidx.lifecycle.ViewModel
import com.vipint.newsapp.data.repository.NewsTypeRepository
import com.vipint.newsapp.utils.NewsType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsTypeViewModel @Inject constructor(private val newsTypeRepository: NewsTypeRepository) :
    ViewModel() {

    fun fetchNewsType(): List<NewsType> {
        return newsTypeRepository.getNewsType()
    }
}