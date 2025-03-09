package com.vipint.newsapp.ui.newssources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipint.newsapp.data.model.SourcesItem
import com.vipint.newsapp.data.repository.NewsSourcesRepository
import com.vipint.newsapp.di.DispatchersProvider
import com.vipint.newsapp.ui.base.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsSourcesViewModel @Inject constructor(
    private val newsSourcesRepository: NewsSourcesRepository,
    private val dispatchersProvider: DispatchersProvider
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<SourcesItem>?>>(UIState.Idle)
    val uiState: StateFlow<UIState<List<SourcesItem>?>> = _uiState

    init {
        fetchNewsSources()
    }

    private fun fetchNewsSources() =
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            newsSourcesRepository.fetchNewsSources()
                .flowOn(dispatchersProvider.io)
                .catch {
                    _uiState.value = UIState.Error(it.message.toString())
                }.collectLatest {
                    _uiState.value = UIState.Success(it)
                }
        }

}