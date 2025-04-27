package com.vipint.newsapp.ui.newssources

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vipint.newsapp.data.model.SourcesItem
import com.vipint.newsapp.ui.base.ShowError
import com.vipint.newsapp.ui.base.ShowLoading
import com.vipint.newsapp.ui.base.UIState

@Composable
fun NewsSourcesRoute(
    newsSourcesViewModel: NewsSourcesViewModel = hiltViewModel(),
    onSourceItemClick: (String) -> Unit
) {
    val newsSourcesState by newsSourcesViewModel.uiState.collectAsStateWithLifecycle()
    NewsSourcesListScreen(newsSourcesState, onSourceItemClick)
}

@Composable
fun NewsSourcesListScreen(
    uiState: UIState<List<SourcesItem>?>,
    onSourceItemClick: (String) -> Unit
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
            uiState.data?.let { NewsSourcesList(it, onSourceItemClick) }
        }
    }
}

@Composable
fun NewsSourcesList(sourcesItemList: List<SourcesItem>, onSourceItemClick: (String) -> Unit) {
    LazyColumn {
        items(sourcesItemList, key = {
            it.name ?: ""
        }) {
            NewsSourceItem(it, onSourceItemClick)
        }
    }
}

@Composable
fun NewsSourceItem(source: SourcesItem?, onSourceItemClick: (String) -> Unit) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 5.dp, end = 12.dp, bottom = 5.dp)
            .clickable {
                onSourceItemClick.invoke(source?.id ?: "")
            }
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(size = 5.dp)
            )
            .padding(10.dp),
        text = source?.name ?: "",
        style = TextStyle(
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    )
}