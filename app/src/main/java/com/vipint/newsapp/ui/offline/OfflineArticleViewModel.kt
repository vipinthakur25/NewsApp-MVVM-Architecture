package com.vipint.newsapp.ui.offline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipint.newsapp.data.local.entitiy.Article
import com.vipint.newsapp.data.repository.OfflineArticleRepository
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineArticleViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val offlineArticleRepository: OfflineArticleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<Article>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<Article>>> = _uiState

    init {
        if (networkHelper.isNetworkConnected()) {
            fetchArticles()
        } else {
            fetchArticlesDirectlyFromDb()
        }
    }

    private fun fetchArticles() {
        viewModelScope.launch {
            offlineArticleRepository.getArticles()
                .flowOn(Dispatchers.IO)
                .catch {
                    _uiState.value = UIState.Error(it.toString())
                }.collect {
                    _uiState.value = UIState.Success(it)
                }
        }
    }

    private fun fetchArticlesDirectlyFromDb() {
        viewModelScope.launch {
            offlineArticleRepository.getArticlesDirectlyFromDb()
                .flowOn(Dispatchers.IO)
                .catch {
                    _uiState.value = UIState.Error(it.toString())
                }.collect {
                    _uiState.value = UIState.Success(it)
                }
        }
    }
}