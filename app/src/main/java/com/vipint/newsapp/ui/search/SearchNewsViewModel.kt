package com.vipint.newsapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.data.repository.SearchNewsRepository
import com.vipint.newsapp.di.DispatchersProvider
import com.vipint.newsapp.ui.base.UIState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchNewsViewModel @Inject constructor(
    private val searchNewsRepository: SearchNewsRepository,
    private val dispatchersProvider: DispatchersProvider
) :
    ViewModel() {

    private val _searchDataStateFlow = MutableStateFlow<UIState<List<ArticlesItem>?>>(UIState.Idle)
    val searchDataStateFlow: StateFlow<UIState<List<ArticlesItem>?>> = _searchDataStateFlow

    private val query = MutableStateFlow("")
    private fun getSearchNews(searchQuery: String): Flow<UIState<List<ArticlesItem>?>> =
        flow {
            emit(UIState.Loading)
            val result = searchNewsRepository.getSearchNews(searchQuery)
            emit(UIState.Success(result.articles))
        }.catch { exception ->
            emit(UIState.Error(exception.message ?: "Unknown Error"))
        }.flowOn(dispatchersProvider.io)

    init {
        getNewsBySearchKey()
    }

    fun getSearchKey(value: String) {
        query.value = value
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun getNewsBySearchKey() {
        viewModelScope.launch {
            query.debounce(300)
                .filter {
                    return@filter it.isNotEmpty()
                }.distinctUntilChanged()
                .flatMapLatest { query ->
                    getSearchNews(query)
                }
                .flowOn(dispatchersProvider.default)
                .collectLatest {
                    _searchDataStateFlow.value = it
                }

        }

    }


}


