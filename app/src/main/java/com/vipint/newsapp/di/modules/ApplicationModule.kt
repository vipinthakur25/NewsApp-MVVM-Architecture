package com.vipint.newsapp.di.modules

import android.content.Context
import com.vipint.newsapp.NewsApplication
import com.vipint.newsapp.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: NewsApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }
}