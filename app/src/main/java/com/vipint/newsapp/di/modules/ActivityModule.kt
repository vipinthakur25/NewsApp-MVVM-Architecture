package com.vipint.newsapp.di.modules

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vipint.newsapp.data.repository.GetCountryRepository
import com.vipint.newsapp.data.repository.NewsRepository
import com.vipint.newsapp.data.repository.NewsSourcesRepository
import com.vipint.newsapp.data.repository.NewsTypeRepository
import com.vipint.newsapp.data.repository.SearchNewsRepository
import com.vipint.newsapp.data.repository.TopHeadlinesRepository
import com.vipint.newsapp.di.ActivityContext
import com.vipint.newsapp.di.DispatchersProvider
import com.vipint.newsapp.ui.base.ViewModelProviderFactory
import com.vipint.newsapp.ui.bottomsheet.NewsTypeViewModel
import com.vipint.newsapp.ui.country.CountriesViewModel
import com.vipint.newsapp.ui.news.NewsViewModel
import com.vipint.newsapp.ui.newssources.NewsSourcesViewModel
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
    fun provideTopHeadlinesViewModel(
        topHeadlinesRepository: TopHeadlinesRepository,
        dispatchersProvider: DispatchersProvider
    ): TopHeadlinesViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(TopHeadlinesViewModel::class) {
            TopHeadlinesViewModel(topHeadlinesRepository, dispatchersProvider)
        })[TopHeadlinesViewModel::class.java]
    }

    @Provides
    fun provideCountriesViewModel(
        countryRepository: GetCountryRepository,
        dispatchersProvider: DispatchersProvider
    ): CountriesViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(CountriesViewModel::class) {
            CountriesViewModel(countryRepository, dispatchersProvider)
        })[CountriesViewModel::class.java]
    }

    @Provides
    fun provideSearchNewsViewModel(
        searchNewsRepository: SearchNewsRepository,
        dispatchersProvider: DispatchersProvider
    ): SearchNewsViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(SearchNewsViewModel::class) {
            SearchNewsViewModel(searchNewsRepository, dispatchersProvider)
        })[SearchNewsViewModel::class.java]
    }

    @Provides
    fun provideNewsTypeViewModel(newsTypeRepository: NewsTypeRepository): NewsTypeViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(NewsTypeViewModel::class) {
            NewsTypeViewModel(newsTypeRepository)
        })[NewsTypeViewModel::class.java]
    }

    @Provides
    fun provideNewsSourcesViewModel(
        newsSourcesRepository: NewsSourcesRepository,
        dispatchersProvider: DispatchersProvider
    ): NewsSourcesViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(NewsSourcesViewModel::class) {
            NewsSourcesViewModel(newsSourcesRepository, dispatchersProvider)
        })[NewsSourcesViewModel::class.java]
    }

    @Provides
    fun provideNewsViewModel(
        newsRepository: NewsRepository,
        dispatchersProvider: DispatchersProvider
    ): NewsViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(NewsViewModel::class) {
            NewsViewModel(newsRepository, dispatchersProvider)
        })[NewsViewModel::class.java]
    }
}