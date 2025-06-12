package com.vipint.newsapp.ui.toHeadlinePagination

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.ui.base.ShowError
import com.vipint.newsapp.ui.base.ShowLoading
import com.vipint.newsapp.ui.topHeadline.NewsRowItem

@Composable
fun TopHeadlinePaginationRoute(
    topHeadlinePaginationViewModel: TopHeadlinePaginationViewModel = hiltViewModel(),
    onArticleClicked: (ArticlesItem?) -> Unit
) {
    val state: LazyPagingItems<ArticlesItem> =
        topHeadlinePaginationViewModel.uiState.collectAsLazyPagingItems()
    TopHeadlinePagingScreen(
        pagingState = state,
        onArticleClicked = onArticleClicked
    )
}


@Composable
fun TopHeadlinePagingScreen(
    modifier: Modifier = Modifier,
    pagingState: LazyPagingItems<ArticlesItem>,
    onArticleClicked: (ArticlesItem?) -> Unit
) {
    PagingArticleList(articles = pagingState, onArticleClicked = onArticleClicked)

    pagingState.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                ShowLoading()
            }

            loadState.refresh is LoadState.Error -> {
                val error = pagingState.loadState.refresh as LoadState.Error
                ShowError(error.error.localizedMessage!!)
            }

            loadState.append is LoadState.Loading -> {
                ShowLoading()
            }

            loadState.append is LoadState.Error -> {
                val error = pagingState.loadState.append as LoadState.Error
                ShowError(error.error.localizedMessage!!)
            }
        }
    }

}

@Composable
fun PagingArticleList(
    articles: LazyPagingItems<ArticlesItem>,
    onArticleClicked: (ArticlesItem?) -> Unit
) {
    LazyColumn {
        items(articles.itemCount, key = { index ->
            articles[index]?.url!!
        }) {
            NewsRowItem(articles[it], onArticleClicked)
        }
    }

}
