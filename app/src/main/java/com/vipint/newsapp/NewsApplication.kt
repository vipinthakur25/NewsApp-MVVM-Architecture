package com.vipint.newsapp

import android.app.Application
import com.vipint.newsapp.di.component.ApplicationComponent
import com.vipint.newsapp.di.modules.ApplicationModule
import com.vipint.newsapp.di.component.DaggerApplicationComponent
import javax.inject.Inject

class NewsApplication : Application() {

    @Inject
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent =
            DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
        applicationComponent.inject(this)
    }
}