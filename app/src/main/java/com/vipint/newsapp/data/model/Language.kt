package com.vipint.newsapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Language(val id: String, val name: String, var isSelected: Boolean = false) : Parcelable
