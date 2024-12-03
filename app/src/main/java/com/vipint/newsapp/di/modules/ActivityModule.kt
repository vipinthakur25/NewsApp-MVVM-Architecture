package com.vipint.newsapp.di.modules

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vipint.newsapp.data.repository.TopHeadlinesRepository
import com.vipint.newsapp.di.ActivityContext
import com.vipint.newsapp.ui.topheadline.TopHeadlinesViewModel
import com.vipint.newsapp.ui.base.ViewModelProviderFactory
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
}