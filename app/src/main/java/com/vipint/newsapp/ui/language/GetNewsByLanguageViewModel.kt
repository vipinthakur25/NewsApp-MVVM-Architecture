package com.vipint.newsapp.ui.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.data.repository.NewsByLanguageRepository
import com.vipint.newsapp.ui.base.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class GetNewsByLanguageViewModel @Inject constructor(private val newsByLanguageRepository: NewsByLanguageRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<ArticlesItem>?>>(UIState.Idle)
    val uiState: StateFlow<UIState<List<ArticlesItem>?>> = _uiState

    fun getNewsByLanguage(language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UIState.Loading
            newsByLanguageRepository.getNewsByLanguage(language).catch {
                _uiState.value = UIState.Error(it.message.toString())
            }.collectLatest {
                _uiState.value = UIState.Success(it)
            }
        }
    }
}