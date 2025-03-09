package com.vipint.newsapp.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.data.repository.NewsRepository
import com.vipint.newsapp.di.DispatchersProvider
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.utils.toPair
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<ArticlesItem>?>>(UIState.Idle)
    val uiState: StateFlow<UIState<List<ArticlesItem>?>> = _uiState

    fun getNewsBySources(sources: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            newsRepository.getNewsBySources(sources)
                .flowOn(dispatchersProvider.io)
                .catch {
                    _uiState.value = UIState.Error(it.message.toString())
                }.collectLatest {
                    _uiState.value = UIState.Success(it)
                }
        }
    }

    fun getNewsByLanguage(language: String) {
        viewModelScope.launch(dispatchersProvider.main) {
            _uiState.value = UIState.Loading
            val languagePair = language.toPair()
            newsRepository.getNewsByLanguage(languagePair.first)
                .zip(newsRepository.getNewsByLanguage(languagePair.second)) { firstResult, secondResult ->
                    (firstResult ?: emptyList()) + (secondResult ?: emptyList())
                }
                .flowOn(dispatchersProvider.io).catch {
                    _uiState.value = UIState.Error(it.message.toString())
                }.collectLatest {
                    _uiState.value = UIState.Success(it)
                }
        }
    }


    fun getNewsByCountry(country: String) {
        viewModelScope.launch(dispatchersProvider.io) {
            _uiState.value = UIState.Loading
            newsRepository.getNewsByCountry(country).catch {
                _uiState.value = UIState.Error(it.message.toString())
            }.collectLatest {
                _uiState.value = UIState.Success(it)
            }
        }
    }
}