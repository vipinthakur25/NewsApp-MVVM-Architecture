package com.vipint.newsapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NetworkAPIKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DatabaseName

