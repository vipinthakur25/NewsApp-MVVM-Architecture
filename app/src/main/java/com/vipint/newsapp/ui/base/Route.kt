package com.vipint.newsapp.ui.base

sealed class Route(val name: String) {
    object HomeScreenRoute : Route("homeScreen")
    object TopHeadlineRoute : Route("topheadline")
    object CountrySelectionRoute : Route("country")
    object LanguageSelectionRoute : Route("language")
    object NewsSourcesRoute : Route("newsBySources")
    object NewsList :
        Route(name = "newslist?sourceId={sourceId}&countryId={countryId}&languageId={languageId}") {
        fun passData(
            sourceId: String = "",
            countryId: String = "",
            languageId: String = ""
        ): String {
            return "newslist?sourceId=$sourceId&countryId=$countryId&languageId=$languageId"
        }
    }

    object SearchNewsRoute : Route("searchNews")
    object OfflineArticleRoute : Route("offlineArticleScreen")
    object TopHeadlinePaginationRoute : Route("topHeadlinePaginationScreen")

}