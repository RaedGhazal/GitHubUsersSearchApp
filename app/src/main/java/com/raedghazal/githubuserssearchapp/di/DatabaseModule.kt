package com.raedghazal.githubuserssearchapp.di

import com.raedghazal.githubuserssearchapp.data.source.Database
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(): Database? {
        return null
    }
}