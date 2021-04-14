package com.raedghazal.githubuserssearchapp.domain.usecase.base

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<T> : UseCase() {

    abstract fun buildUseCaseSingle(queryText: String, pageNumber: Int): Single<T>

    fun execute(
        queryText: String,
        pageNumber: Int,
        onSuccess: ((t: T) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: (() -> Unit) = {},
    ) {
        disposeLast()
        lastDisposable = buildUseCaseSingle(queryText, pageNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate(onFinished)
            .subscribe(onSuccess, onError)

        lastDisposable.let {
            compositeDisposable.add(it)
        }
    }
}