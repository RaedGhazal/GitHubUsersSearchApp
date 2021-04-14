package com.raedghazal.githubuserssearchapp.di

import android.content.Context
import com.raedghazal.githubuserssearchapp.di.users.UsersViewModeModule
import com.raedghazal.githubuserssearchapp.presentation.userdetails.UserDetailsFragment
import com.raedghazal.githubuserssearchapp.MainActivity
import com.raedghazal.githubuserssearchapp.presentation.users.SearchUsersFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class,
        DatabaseModule::class,
        ViewModelFactoryModule::class,
        UsersViewModeModule::class]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(searchUsersFragment: SearchUsersFragment)

    fun inject(userDetailsFragment: UserDetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}