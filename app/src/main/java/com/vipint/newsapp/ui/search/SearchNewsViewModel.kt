package com.vipint.newsapp.ui.search

import androidx.lifecycle.ViewModel
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.data.repository.SearchNewsRepository
import com.vipint.newsapp.ui.base.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SearchNewsViewModel @Inject constructor(private val searchNewsRepository: SearchNewsRepository) :
    ViewModel() {

    fun getSearchNews(searchQuery: String): Flow<UIState<List<ArticlesItem>?>> =
        flow {
            emit(UIState.Loading)
            val result = searchNewsRepository.getSearchNews(searchQuery)
            emit(UIState.Success(result.articles))
        } .catch { exception ->
            emit(UIState.Error(exception.message ?: "Unknown Error"))
        }.flowOn(Dispatchers.IO)
}


