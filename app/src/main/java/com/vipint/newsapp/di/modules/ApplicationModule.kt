package com.vipint.newsapp.di.modules

import android.content.Context
import androidx.room.Room
import com.vipint.newsapp.data.api.AuthInterceptor
import com.vipint.newsapp.data.api.NetworkService
import com.vipint.newsapp.data.local.AppDatabase
import com.vipint.newsapp.data.local.AppDatabaseService
import com.vipint.newsapp.data.local.DatabaseService
import com.vipint.newsapp.di.BaseUrl
import com.vipint.newsapp.di.DatabaseName
import com.vipint.newsapp.di.DispatchersProvider
import com.vipint.newsapp.di.DispatchersProviderImpl
import com.vipint.newsapp.di.NetworkAPIKey
import com.vipint.newsapp.utils.AppConstants
import com.vipint.newsapp.utils.NetworkHelper
import com.vipint.newsapp.utils.NetworkHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule() {


    @BaseUrl
    @Provides
    fun providesBaseUrl(): String = "https://newsapi.org/v2/"

    @DatabaseName
    @Provides
    fun provideDatabaseName(): String = "news-database"

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
        authInterceptor: AuthInterceptor, loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val client = OkHttpClient.Builder().addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor).build()
        return client
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper =
        NetworkHelperImpl(context)

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient

    ): NetworkService {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(gsonConverterFactory)
            .client(okHttpClient).build().create(NetworkService::class.java)
    }


    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context,
        @DatabaseName databaseName: String
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            name = databaseName,
        ).build()
    }

    @Provides
    @Singleton
    fun provideAppDatabaseService(appDatabase: AppDatabase): DatabaseService =
        AppDatabaseService(appDatabase)
}

