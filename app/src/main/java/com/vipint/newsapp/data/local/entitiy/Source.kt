package com.vipint.newsapp.data.local.entitiy

import androidx.room.ColumnInfo

data class Source(
    @ColumnInfo(name = "sourceId")
    val id: String?,
    @ColumnInfo(name = "sourceName")
    val name: String = ""
)