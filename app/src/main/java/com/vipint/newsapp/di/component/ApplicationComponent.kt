package com.vipint.newsapp.di.component

import android.content.Context
import com.vipint.newsapp.NewsApplication
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
}