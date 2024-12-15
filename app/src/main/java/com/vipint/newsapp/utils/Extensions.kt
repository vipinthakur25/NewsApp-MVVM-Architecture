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