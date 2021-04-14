package com.raedghazal.githubuserssearchapp.di

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.raedghazal.githubuserssearchapp.data.repository.UserRepositoryImpl
import com.raedghazal.githubuserssearchapp.data.source.Database
import com.raedghazal.githubuserssearchapp.data.source.remote.RetrofitApi
import com.raedghazal.githubuserssearchapp.domain.repository.UserRepository
import com.raedghazal.githubuserssearchapp.util.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .build()

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory =
        RxJava2CallAdapterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofitApi(retrofit: Retrofit): RetrofitApi = retrofit.create(
        RetrofitApi::
        class.java
    )

    @Singleton
    @Provides
    fun provideUserRepository(retrofitApi: RetrofitApi, database: Database?,context: Context): UserRepository =
        UserRepositoryImpl(retrofitApi, database,context)

    @Singleton
    @Provides
    fun provideSavedStateHandler():SavedStateHandle{
        return SavedStateHandle()
    }

}
