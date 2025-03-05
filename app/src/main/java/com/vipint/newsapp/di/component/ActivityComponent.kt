package com.vipint.newsapp.di.component

import com.vipint.newsapp.di.ActivityScope
import com.vipint.newsapp.di.modules.ActivityModule
import com.vipint.newsapp.ui.country.CountriesActivity
import com.vipint.newsapp.ui.language.LanguageActivity
import com.vipint.newsapp.ui.news.NewsActivity
import com.vipint.newsapp.ui.newssources.NewsSourcesActivity
import com.vipint.newsapp.ui.search.SearchActivity
import com.vipint.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {


    fun inject(activity: TopHeadlineActivity)

    fun inject(activity: SearchActivity)

    fun inject(activity: NewsSourcesActivity)

    fun inject(activity: CountriesActivity)

    fun inject(activity: LanguageActivity)

    fun inject(activity: NewsActivity)

}