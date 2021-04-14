package com.raedghazal.githubuserssearchapp

import android.app.Application
import com.raedghazal.githubuserssearchapp.di.AppComponent
import com.raedghazal.githubuserssearchapp.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent:AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }

}