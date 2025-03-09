package com.vipint.newsapp.utils

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun AppCompatEditText.getTextChangedStateFlow(): StateFlow<String> {
    val query = MutableStateFlow("")
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            query.value = p0.toString()
        }

        override fun afterTextChanged(p0: Editable?) {

        }

    })
    return query
}

/**
 * val extraLanguage =
 * this.getString(EXTRA_LANGUAGE)?.split(",")?.toPairFromList()
 */

fun String.toPair(): Pair<String, String> {
    val splitList = this.split(",")
    if (this.split(",").size > 2)
        throw IllegalArgumentException("List is not of length 2!")
    return Pair(splitList[0], splitList[1])
}

/*
fun <T> List<T>.toPairFromList(): Pair<T, T> {
    if (this.size != 2) {
        throw IllegalArgumentException("List is not of length 2!")
    }
    return Pair(this[0], this[1])
}*/
