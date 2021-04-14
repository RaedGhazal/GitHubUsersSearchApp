package com.raedghazal.githubuserssearchapp.di.users

import androidx.lifecycle.ViewModel
import com.raedghazal.githubuserssearchapp.di.ViewModelKey
import com.raedghazal.githubuserssearchapp.presentation.users.UsersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UsersViewModeModule {

    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel::class)
    abstract fun bindUsersViewModel(usersViewModel: UsersViewModel): ViewModel
}