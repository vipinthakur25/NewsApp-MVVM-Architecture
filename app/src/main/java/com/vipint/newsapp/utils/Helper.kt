package com.vipint.newsapp.utils

 inline fun <T, R> getStringPairFromList(
    languageList: List<T>,
    transform: (T) -> R
): List<R> {
    return languageList.map {
        transform(it)
    }
}