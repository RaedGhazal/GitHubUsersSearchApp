package com.raedghazal.githubuserssearchapp.domain.usecase.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class UseCase {

    protected val compositeDisposable = CompositeDisposable()
    lateinit var lastDisposable: Disposable

    fun disposeLast() {
        if (this::lastDisposable.isInitialized)
            lastDisposable.let {
                if (!it.isDisposed)
                    it.dispose()
            }
    }

    fun dispose() {
        compositeDisposable.clear()
    }
}