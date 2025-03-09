package com.vipint.newsapp.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.data.repository.TopHeadlinesRepository
import com.vipint.newsapp.di.DispatchersProvider
import com.vipint.newsapp.ui.base.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlinesViewModel @Inject constructor(
    private val topHeadlinesRepository: TopHeadlinesRepository,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow<UIState<List<ArticlesItem>?>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<ArticlesItem>?>> = _uiState


    fun fetchNews(country: String) {
        viewModelScope.launch {
            topHeadlinesRepository.getTopHeadline(country)
                .flowOn(dispatchersProvider.io)
                .catch {
                    _uiState.value = UIState.Error(it.message.toString())
                }
                .collectLatest {
                    _uiState.value = UIState.Success(it)
                }

        }
    }
}