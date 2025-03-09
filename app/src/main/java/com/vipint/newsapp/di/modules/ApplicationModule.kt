package com.vipint.newsapp.di.modules

import android.content.Context
import com.vipint.newsapp.NewsApplication
import com.vipint.newsapp.data.api.AuthInterceptor
import com.vipint.newsapp.data.api.NetworkService
import com.vipint.newsapp.di.ApplicationContext
import com.vipint.newsapp.di.BaseUrl
import com.vipint.newsapp.di.DispatchersProvider
import com.vipint.newsapp.di.DispatchersProviderImpl
import com.vipint.newsapp.di.NetworkAPIKey
import com.vipint.newsapp.utils.AppConstants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: NewsApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }

    @BaseUrl
    @Provides
    fun providesBaseUrl(): String = "https://newsapi.org/v2/"

    @NetworkAPIKey
    @Provides
    fun provideApiKey(): String = AppConstants.API_KEY

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideAuthInterceptor(@NetworkAPIKey apiKey: String) = AuthInterceptor(apiKey)

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideCoroutineDispatchers(): DispatchersProvider = DispatchersProviderImpl()

    @Provides
    @Singleton
    fun provideOkhttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val client = OkHttpClient.Builder().addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor).build()
        return client
    }

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient

    ): NetworkService {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
            .create(NetworkService::class.java)
    }
}

