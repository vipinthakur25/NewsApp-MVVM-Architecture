package com.vipint.newsapp.di.component

import com.vipint.newsapp.di.ActivityScope
import com.vipint.newsapp.di.modules.ActivityModule
import com.vipint.newsapp.ui.news_by_sources.NewsBySourcesActivity
import com.vipint.newsapp.ui.news_sources.NewsSourcesActivity
import com.vipint.newsapp.ui.search.SearchActivity
import com.vipint.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Component
import javax.inject.Singleton

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    @Singleton
    fun inject(activity: TopHeadlineActivity)

    @Singleton
    fun injectSearchActivity(activity: SearchActivity)

    @Singleton
    fun injectNewsSourcesActivity(activity: NewsSourcesActivity)

    @Singleton
    fun injectNewsBySourcesActivity(activity: NewsBySourcesActivity)

}