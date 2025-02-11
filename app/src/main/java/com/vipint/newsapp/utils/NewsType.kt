package com.vipint.newsapp.utils

enum class NewsType(val type: String) {
    TOP_HEADLINE("Top Headlines"),
    NEWS_SOURCES("News Sources"),
    COUNTRIES("Countries"),
    LANGUAGE("Languages");


    // This companion object helps with serialization
    companion object {
        fun fromString(type: String): NewsType? {
            return entries.find { it.type == type }
        }
    }
}