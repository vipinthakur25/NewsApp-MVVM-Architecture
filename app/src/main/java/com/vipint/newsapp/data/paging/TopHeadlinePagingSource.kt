package com.vipint.newsapp.data.paging

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vipint.newsapp.data.api.NetworkService
import com.vipint.newsapp.data.model.ArticlesItem
import java.io.IOException

class TopHeadlinePagingSource(private val networkService: NetworkService) :
    PagingSource<Int, ArticlesItem>() {
    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        return try {
            val currentPage = params.key ?: 1
            val news = networkService.fetchTopHeadlinePaginated(
                country = "us",
                page = currentPage,
                pageSize = 20
            )
            LoadResult.Page(
                data = news.body()?.articles ?: emptyList(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (news.body()?.articles!!.isEmpty()) null else currentPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}

