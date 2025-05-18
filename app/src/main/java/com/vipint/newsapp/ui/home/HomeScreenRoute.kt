package com.vipint.newsapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.vipint.newsapp.R
import com.vipint.newsapp.ui.home.uicomponets.MainScreenItemSection

@Composable
fun HomeScreenRoute(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        content = {
            MainScreenItemSection(
                title = stringResource(R.string.top_headline),
                navController = navController,
                routeName = "topheadline"
            )
            MainScreenItemSection(
                title = stringResource(R.string.countries),
                navController = navController,
                routeName = "country"
            )
            MainScreenItemSection(
                title = stringResource(R.string.language),
                navController = navController,
                routeName = "language"
            )
            MainScreenItemSection(
                title = stringResource(R.string.news_sources),
                navController = navController,
                routeName = "newsBySources"
            )
            MainScreenItemSection(
                title = stringResource(R.string.search),
                navController = navController,
                routeName = "searchNews"
            )
            MainScreenItemSection(
                title = stringResource(R.string.offlineArticleScreen),
                navController = navController,
                routeName = "offlineArticleScreen"
            )
            MainScreenItemSection(
                title = stringResource(R.string.topHeadlinePaginationScreen),
                navController = navController,
                routeName = "topHeadlinePaginationScreen"
            )
        })
}