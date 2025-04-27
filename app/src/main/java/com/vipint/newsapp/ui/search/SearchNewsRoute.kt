package com.vipint.newsapp.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vipint.newsapp.data.model.ArticlesItem
import com.vipint.newsapp.ui.base.ShowError
import com.vipint.newsapp.ui.base.ShowLoading
import com.vipint.newsapp.ui.base.UIState
import com.vipint.newsapp.ui.topHeadline.ArticleList

@Composable
fun SearchNewsRoute(
    onArticleClicked: (ArticlesItem?) -> Unit,
    searchNewsViewModel: SearchNewsViewModel = hiltViewModel()
) {
    SearchNewsComponent(searchNewsViewModel, onArticleClicked)

}

@Composable
fun SearchNewsScreen(
    uiState: UIState<List<ArticlesItem>?>,
    onArticleClicked: (ArticlesItem?) -> Unit
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
            uiState.data?.let { ArticleList(articles = it, onArticleClicked = onArticleClicked) }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNewsComponent(
    searchNewsViewModel: SearchNewsViewModel,
    onArticleClicked: (ArticlesItem?) -> Unit,
) {
    val searchNewsState by searchNewsViewModel.searchDataStateFlow.collectAsStateWithLifecycle()
    var value by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        query = value,
        onSearch = {
            searchNewsViewModel.getSearchKey(it)
        },
        onQueryChange = {
            value = it
        },
        onActiveChange = {
            active = it
        },
        active = active,
        placeholder = {
            Text("Search news here........")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")

        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (value.isNotEmpty()) {
                            value = ""
                        } else {
                            active = false
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close icon"
                )
            }
        }, content = {
            SearchNewsScreen(searchNewsState, onArticleClicked)
        },
        shape = SearchBarDefaults.inputFieldShape,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    )

}