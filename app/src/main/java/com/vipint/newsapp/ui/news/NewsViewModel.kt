package com.vipint.newsapp.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.data.repository.NewsRepository
import com.vipint.newsapp.ui.base.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<ArticlesItem>?>>(UIState.Idle)
    val uiState: StateFlow<UIState<List<ArticlesItem>?>> = _uiState

    fun getNewsBySources(sources: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UIState.Loading
            newsRepository.getNewsBySources(sources).catch {
                _uiState.value = UIState.Error(it.message.toString())
            }.collectLatest {
                _uiState.value = UIState.Success(it)
            }
        }
    }

    fun getNewsByLanguage(language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UIState.Loading
            newsRepository.getNewsByLanguage(language).catch {
                _uiState.value = UIState.Error(it.message.toString())
            }.collectLatest {
                _uiState.value = UIState.Success(it)
            }
        }
    }

    fun getNewsByCountry(country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UIState.Loading
            newsRepository.getNewsByCountry(country).catch {
                _uiState.value = UIState.Error(it.message.toString())
            }.collectLatest {
                _uiState.value = UIState.Success(it)
            }
        }
    }
}