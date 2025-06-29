package com.vipint.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class TopHeadLineResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("totalResults") val totalResults: Int = 0,
    @field:SerializedName("articles") val articles: List<ArticlesItem>? = null
)

data class ArticlesItem(

    @field:SerializedName("publishedAt") val publishedAt: String? = null,

    @field:SerializedName("author") val author: String? = null,

    @field:SerializedName("urlToImage") val urlToImage: String? = null,

    @field:SerializedName("description") val description: String? = null,

    @field:SerializedName("source") val source: Source? = null,

    @field:SerializedName("title") val title: String? = null,

    @field:SerializedName("url") val url: String? = null,

    @field:SerializedName("content") val content: String? = null
)

data class Source(

    @field:SerializedName("name") val name: String? = null,

    @field:SerializedName("id") val id: String? = null
)
