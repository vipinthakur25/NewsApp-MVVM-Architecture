package com.vipint.newsapp.ui.offline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vipint.newsapp.data.local.entitiy.Article
import com.vipint.newsapp.ui.base.HorizontalDivider
import com.vipint.newsapp.ui.base.ShowError
import com.vipint.newsapp.ui.base.ShowLoading
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.ui.topHeadline.NewsImage
import com.vipint.newsapp.ui.topHeadline.NewsTitle
import com.vipint.newsapp.ui.topHeadline.SourceText

@Composable
fun OfflineArticleScreenRoute(
    onArticleClicked: (Article?) -> Unit,
    offlineArticleViewModel: OfflineArticleViewModel = hiltViewModel()
) {
    val uiState by offlineArticleViewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        is UIState.Error -> {
            ShowError((uiState as UIState.Error).message)
        }

        UIState.Idle -> {

        }

        UIState.Loading -> {
            ShowLoading()

        }

        is UIState.Success -> {
            OfflineArticleList(
                (uiState as UIState.Success<List<Article>>).data,
                onArticleClicked = onArticleClicked
            )

        }
    }
}

@Composable
fun OfflineArticleList(articles: List<Article>, onArticleClicked: (Article?) -> Unit) {
    LazyColumn {
        items(articles, key = {
            it.url
        }) {
            OfflineNewsRowItem(it, onArticleClicked)
        }
    }

}

@Composable
fun OfflineNewsRowItem(
    article: Article?, onArticleClicked: (Article?) -> Unit
) {
    Column(modifier = Modifier.padding(10.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.clickable {
                onArticleClicked(article)
            }
        ) {
            NewsImage(
                imageUrl = article?.imageUrl ?: "",
                contentDescription = article?.title ?: "",
                modifier = Modifier
                    .weight(0.4f)
                    .aspectRatio(1f)
            )
            Column(
                modifier = Modifier.weight(0.6f),
                horizontalAlignment = Alignment.Start,
            ) {
                NewsTitle(title = article?.title ?: "")
                SourceText(source = article?.source?.name ?: "")
            }
        }
        HorizontalDivider()
    }
}