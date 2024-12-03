package com.vipint.newsapp.ui.base

sealed interface UIState<out T> {

    data class Success<T>(val data: T) : UIState<T>

    data class Error(val message: String) : UIState<Nothing>

    data object Loading : UIState<Nothing>
}