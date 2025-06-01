package com.vipint.newsapp.ui.base

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vipint.newsapp.ui.toHeadlinePagination.TopHeadlinePaginationRoute
import com.vipint.newsapp.ui.country.CountrySelectionRoute
import com.vipint.newsapp.ui.home.HomeScreenRoute
import com.vipint.newsapp.ui.language.LanguageSelectionRoute
import com.vipint.newsapp.ui.news.NewsRoute
import com.vipint.newsapp.ui.newssources.NewsSourcesRoute
import com.vipint.newsapp.ui.offline.OfflineArticleScreenRoute
import com.vipint.newsapp.ui.search.SearchNewsRoute
import com.vipint.newsapp.ui.topHeadline.TopHeadlineRoute
import com.vipint.newsapp.utils.AppConstants

@Composable
fun NewsNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController, startDestination = Route.HomeScreenRoute.name
    ) {
        composable(route = Route.HomeScreenRoute.name) {
            HomeScreenRoute(navController = navController)
        }
        composable(route = Route.TopHeadlineRoute.name) {
            TopHeadlineRoute(onArticleClicked = {
                openCustomChromeTab(context = context, url = it?.url)
            })
        }
        composable(route = Route.CountrySelectionRoute.name) {
            CountrySelectionRoute(onCountryItemClick = {
                navController.navigate(route = Route.NewsList.passData(countryId = it))
            })
        }
        composable(route = Route.NewsSourcesRoute.name) {
            NewsSourcesRoute(onSourceItemClick = {
                navController.navigate(route = Route.NewsList.passData(sourceId = it))
            })
        }
        composable(
            route = Route.LanguageSelectionRoute.name,
        ) {
            LanguageSelectionRoute(onLanguageItemClick = {
                navController.navigate(route = Route.NewsList.passData(languageId = it))

            })
        }
        composable(route = Route.NewsList.name,
            arguments = listOf(
                navArgument(AppConstants.SOURCE_ID) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(AppConstants.COUNTRY_ID) {
                    type = NavType.StringType
                    defaultValue = ""

                },
                navArgument(AppConstants.LANGUAGE_ID) {
                    type = NavType.StringType
                    defaultValue = ""

                }
            )
        ) { it ->
            val sourceId = it.arguments?.getString(AppConstants.SOURCE_ID).toString()
            val countryId = it.arguments?.getString(AppConstants.COUNTRY_ID).toString()
            val languageId = it.arguments?.getString(AppConstants.LANGUAGE_ID).toString()

            NewsRoute(
                sourceId = sourceId,
                countryId = countryId,
                languageId = languageId,
                onArticleClicked = {
                    openCustomChromeTab(context = context, url = it?.url)
                },
                onRetryClick = {
                    navController.popBackStack()
                })
        }
        composable(route = Route.SearchNewsRoute.name) {
            SearchNewsRoute(onArticleClicked = {
                openCustomChromeTab(context = context, url = it?.url)
            })
        }
        composable(route = Route.OfflineArticleRoute.name) {
            OfflineArticleScreenRoute(onArticleClicked = {
                openCustomChromeTab(context, url = it?.url)
            })
        }
        composable(route = Route.TopHeadlinePaginationRoute.name) {
            TopHeadlinePaginationRoute(onArticleClicked = {
                openCustomChromeTab(context, url = it?.url)
            })
        }

    }
}

fun openCustomChromeTab(context: Context, url: String?) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}