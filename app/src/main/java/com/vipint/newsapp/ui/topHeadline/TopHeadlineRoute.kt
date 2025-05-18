package com.vipint.newsapp.ui.topHeadline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.ui.base.HorizontalDivider
import com.vipint.newsapp.ui.base.ShowError
import com.vipint.newsapp.ui.base.ShowLoading
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.utils.AppConstants

@Composable
fun TopHeadlineRoute(
    onArticleClicked: (ArticlesItem?) -> Unit,
    topHeadlinesViewModel: TopHeadlinesViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        topHeadlinesViewModel.fetchNews(AppConstants.COUNTRY)
    }
    val topHeadlineUiState by topHeadlinesViewModel.uiState.collectAsStateWithLifecycle()
    TopHeadlineScreen(
        uiState = topHeadlineUiState,
        onArticleClicked = onArticleClicked,
        onRetryClick = {
            topHeadlinesViewModel.fetchNews(AppConstants.COUNTRY)
        }
    )

}

@Composable
fun TopHeadlineScreen(
    uiState: UIState<List<ArticlesItem>?>,
    onArticleClicked: (ArticlesItem?) -> Unit,
    onRetryClick: () -> Unit
) {
    when (uiState) {
        is UIState.Error -> {
            ShowError(uiState.message, onRetryClick)
        }

        UIState.Idle -> {

        }

        UIState.Loading -> {
            ShowLoading()
        }

        is UIState.Success -> {
            uiState.data?.let { ArticleList(articles = it, onArticleClicked = onArticleClicked) }
        }
    }
}

@Composable
fun ArticleList(articles: List<ArticlesItem>, onArticleClicked: (ArticlesItem?) -> Unit) {
    LazyColumn {
        items(articles, key = {
            it.url!!
        }) {
            NewsRowItem(it, onArticleClicked)
        }
    }

}


@Composable
fun NewsRowItem(
    article: ArticlesItem?, onArticleClicked: (ArticlesItem?) -> Unit
) {
    Column(modifier = Modifier.padding(10.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.clickable {
                onArticleClicked(article)
            }
        ) {
            NewsImage(
                imageUrl = article?.urlToImage ?: "",
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

@Composable
fun NewsImage(imageUrl: String, contentDescription: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}


@Composable
fun NewsTitle(title: String) {
    Text(
        text = title,
        style = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
        ),
        maxLines = 4
    )
}

@Composable
fun SourceText(source: String) {
    if (source.isNotEmpty()) {
        Text(
            text = "By $source",
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray
            ),
            maxLines = 1,
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
        )
    }
}

