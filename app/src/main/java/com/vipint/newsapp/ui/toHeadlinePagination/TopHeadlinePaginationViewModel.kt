package com.vipint.newsapp.ui.toHeadlinePagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.data.repository.TopHeadlinePagingSourceRepository
import com.vipint.newsapp.di.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinePaginationViewModel @Inject constructor(
    private val topHeadlinesRepository: TopHeadlinePagingSourceRepository,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<PagingData<ArticlesItem>>(value = PagingData.empty())
    val uiState: StateFlow<PagingData<ArticlesItem>> = _uiState

    init {
        fetchNews()
    }

    private fun fetchNews() = viewModelScope.launch {
        topHeadlinesRepository.getPaginatedTopHeadline().collect {
            _uiState.value = it
        }
    }


}