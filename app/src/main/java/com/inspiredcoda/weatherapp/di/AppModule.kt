package com.inspiredcoda.weatherapp.di

import android.content.Context
import com.inspiredcoda.weatherapp.data.Api
import com.inspiredcoda.weatherapp.data.InternetInterceptor
import com.inspiredcoda.weatherapp.data.WeatherRepository
import com.inspiredcoda.weatherapp.data.WeatherRepositoryImpl
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
class AppModule {

    @Singleton
    @Provides
    fun provideClient(@ApplicationContext context: Context): OkHttpClient {
        val interceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(InternetInterceptor(context))
            .build()
    }

    @Singleton
    @Provides
    fun providesApi(logger: OkHttpClient) = Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(logger)
        .build()
        .create(Api::class.java)

    @Singleton
    @Provides
    fun providesRepository(api: Api): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }

}