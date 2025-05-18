package com.vipint.newsapp.data.model

import com.google.gson.annotations.SerializedName
import com.vipint.newsapp.data.local.entitiy.Article

data class TopHeadLineResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("totalResults") val totalResults: Int = 0,
    @field:SerializedName("articles") val articles: List<ArticlesItem>
)

data class ArticlesItem(

    @field:SerializedName("publishedAt") val publishedAt: String? = null,

    @field:SerializedName("author") val author: String? = null,

    @field:SerializedName("urlToImage") val urlToImage: String? = null,

    @field:SerializedName("description") val description: String? = null,

    @field:SerializedName("source") val source: Source,

    @field:SerializedName("title") val title: String? = null,

    @field:SerializedName("url") val url: String? = null,

    @field:SerializedName("content") val content: String? = null
)

fun ArticlesItem.toArticleEntity(): Article {
    return Article(
        title = this.title ?: "",
        description = this.description,
        url = this.url ?: "",
        imageUrl = this.urlToImage,
        source = this.source.ToEntity()
    )
}

data class Source(

    @field:SerializedName("name") val name: String? = null,

    @field:SerializedName("id") val id: String? = null
)

fun Source.ToEntity(): com.vipint.newsapp.data.local.entitiy.Source {
    return com.vipint.newsapp.data.local.entitiy.Source(
        id = this.id,
        name = this.name ?: ""
    )
}