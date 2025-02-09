package com.vipint.newsapp.di.component

import android.content.Context
import com.vipint.newsapp.NewsApplication
import com.vipint.newsapp.data.api.NetworkService
import com.vipint.newsapp.data.repository.GetCountryRepository
import com.vipint.newsapp.data.repository.GetLanguageRepository
import com.vipint.newsapp.data.repository.NewsByLanguageRepository
import com.vipint.newsapp.data.repository.NewsBySourcesRepository
import com.vipint.newsapp.data.repository.NewsSourcesRepository
import com.vipint.newsapp.data.repository.NewsTypeRepository
import com.vipint.newsapp.data.repository.SearchNewsRepository
import com.vipint.newsapp.data.repository.TopHeadlinesRepository
import com.vipint.newsapp.di.ApplicationContext
import com.vipint.newsapp.di.modules.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: NewsApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getTopHeadlineRepository(): TopHeadlinesRepository

    fun getNewsSourcesRepository(): NewsSourcesRepository

    fun getSearchNewsRepository(): SearchNewsRepository

    fun getNewsTypeRepository(): NewsTypeRepository

    fun getLanguageRepository(): GetLanguageRepository

    fun getTopHeadlineByNewsSourcesRepository(): NewsBySourcesRepository

    fun getCountriesRepository(): GetCountryRepository

    fun getNewsByLanguageRepository(): NewsByLanguageRepository

    fun getNetworkService(): NetworkService
}