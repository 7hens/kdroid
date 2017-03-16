package org.chx.kdroid.candy.rx

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

open class EmptyObserver<T> : Observer<T> {
    override fun onError(e: Throwable?) {
    }

    override fun onNext(t: T) {
    }

    override fun onSubscribe(d: Disposable?) {
    }

    override fun onComplete() {
    }

    companion object {
        fun<T> with (onNext: (T)->Unit) = object : EmptyObserver<T>() {
            override fun onNext(t: T) = onNext(t)
        }
    }
}
