package com.vipint.newsapp.di.modules

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vipint.newsapp.data.repository.SearchNewsRepository
import com.vipint.newsapp.data.repository.TopHeadlinesRepository
import com.vipint.newsapp.di.ActivityContext
import com.vipint.newsapp.ui.base.ViewModelProviderFactory
import com.vipint.newsapp.ui.search.SearchNewsViewModel
import com.vipint.newsapp.ui.topheadline.TopHeadlinesViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideTopHeadlinesViewModel(topHeadlinesRepository: TopHeadlinesRepository): TopHeadlinesViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(TopHeadlinesViewModel::class) {
            TopHeadlinesViewModel(topHeadlinesRepository)
        })[TopHeadlinesViewModel::class.java]
    }

    @Provides
    fun provideSearchNewsViewModel(searchNewsRepository: SearchNewsRepository): SearchNewsViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(SearchNewsViewModel::class) {
            SearchNewsViewModel(searchNewsRepository)
        })[SearchNewsViewModel::class.java]
    }

}