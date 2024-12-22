package com.vipint.newsapp.di.modules

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vipint.newsapp.data.repository.NewsBySourcesRepository
import com.vipint.newsapp.data.repository.NewsSourcesRepository
import com.vipint.newsapp.data.repository.NewsTypeRepository
import com.vipint.newsapp.data.repository.SearchNewsRepository
import com.vipint.newsapp.data.repository.TopHeadlinesRepository
import com.vipint.newsapp.di.ActivityContext
import com.vipint.newsapp.ui.base.ViewModelProviderFactory
import com.vipint.newsapp.ui.bottomsheet.NewsTypeViewModel
import com.vipint.newsapp.ui.news_by_sources.NewsBySourcesViewModel
import com.vipint.newsapp.ui.news_sources.NewsSourcesViewModel
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

    @Provides
    fun provideNewsTypeViewModel(newsTypeRepository: NewsTypeRepository): NewsTypeViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(NewsTypeViewModel::class) {
            NewsTypeViewModel(newsTypeRepository)
        })[NewsTypeViewModel::class.java]
    }

    @Provides
    fun provideNewsSourcesViewModel(newsSourcesRepository: NewsSourcesRepository) : NewsSourcesViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(NewsSourcesViewModel::class) {
            NewsSourcesViewModel(newsSourcesRepository)
        })[NewsSourcesViewModel::class.java]
    }

    @Provides
    fun provideNewsBySourcesViewModel(newsBySourcesRepository: NewsBySourcesRepository) : NewsBySourcesViewModel{
        return ViewModelProvider(activity, ViewModelProviderFactory(NewsBySourcesViewModel::class) {
            NewsBySourcesViewModel(newsBySourcesRepository)
        })[NewsBySourcesViewModel::class.java]
    }
}