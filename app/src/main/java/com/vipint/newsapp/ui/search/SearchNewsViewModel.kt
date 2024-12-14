package com.vipint.newsapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.data.repository.SearchNewsRepository
import com.vipint.newsapp.ui.base.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchNewsViewModel @Inject constructor(private val searchNewsRepository: SearchNewsRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow<UIState<List<ArticlesItem>?>>(UIState.Idle)
    val uiState: StateFlow<UIState<List<ArticlesItem>?>> = _uiState

    fun getSearchNews(searchQuery: String) {
        viewModelScope.launch {
            searchNewsRepository.getSearchNews(searchQuery).catch {
                _uiState.value = UIState.Error(it.message.toString())
            }.collectLatest {
                _uiState.value = UIState.Success(it)

            }
        }
    }

}