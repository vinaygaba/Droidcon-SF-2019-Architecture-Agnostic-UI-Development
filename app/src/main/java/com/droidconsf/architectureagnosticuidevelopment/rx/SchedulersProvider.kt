package com.droidconsf.architectureagnosticuidevelopment.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulersProvider @Inject constructor() {

    fun io() = Schedulers.io()

    fun computation() = Schedulers.computation()

    fun ui() = AndroidSchedulers.mainThread()
}