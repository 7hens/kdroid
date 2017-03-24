package org.chx.kdroid.candy.rx

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers

fun<T> Observable<T>.observeOnMainThread() = observeOn(AndroidSchedulers.mainThread())!!
fun<T> Observable<T>.observeOnMainThread(observer: Observer<T>) = observeOnMainThread().subscribe(observer)