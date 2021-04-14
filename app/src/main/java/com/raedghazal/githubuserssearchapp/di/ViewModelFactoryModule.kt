package com.raedghazal.githubuserssearchapp.di

import androidx.lifecycle.ViewModelProvider
import com.raedghazal.githubuserssearchapp.di.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(modelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}