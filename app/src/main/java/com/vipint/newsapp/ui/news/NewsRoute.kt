package com.vipint.newsapp.ui.news

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.ui.base.EmptyStateUIScreen
import com.vipint.newsapp.ui.base.ShowError
import com.vipint.newsapp.ui.base.ShowLoading
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.ui.topHeadline.ArticleList

@Composable
fun NewsRoute(
    languageId: String? = null,
    countryId: String? = null,
    sourceId: String? = null,
    newsViewModel: NewsViewModel = hiltViewModel(),
    onArticleClicked: (ArticlesItem?) -> Unit,
    onRetryClick: () -> Unit
) {
    val newsListState by newsViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        if (!languageId.isNullOrEmpty()) {
            newsViewModel.getNewsByLanguage(languageId)
        }
        if (!countryId.isNullOrEmpty()) {
            newsViewModel.getNewsByCountry(countryId)
        }
        if (!sourceId.isNullOrEmpty()) {
            newsViewModel.getNewsBySources(sourceId)
        }

    }
    NewsListScreen(
        uiState = newsListState,
        onArticleClicked = onArticleClicked,
        onRetryClick = onRetryClick
    )
}

@Composable
fun NewsListScreen(
    uiState: UIState<List<ArticlesItem>?>,
    onArticleClicked: (ArticlesItem?) -> Unit,
    onRetryClick: () -> Unit
) {
    when (uiState) {
        is UIState.Error -> {
            ShowError(uiState.message)
        }

        UIState.Idle -> {

        }

        UIState.Loading -> {
            ShowLoading()
        }

        is UIState.Success -> {
            uiState.data?.let {
                if (it.isNotEmpty()) {
                    ArticleList(articles = it, onArticleClicked = onArticleClicked)
                } else EmptyStateUIScreen(onCLick = onRetryClick)
            }
        }
    }
}
