package com.droidconsf.architectureagnosticuidevelopment.viewmodels

import com.droidconsf.architectureagnosticuidevelopment.BuildConfig
import com.droidconsf.architectureagnosticuidevelopment.rx.SchedulersProvider
import com.droidconsf.architectureagnosticuidevelopment.usecases.GetComics
import com.droidconsf.architectureagnosticuidevelopment.util.md5
import java.util.Date

class MainViewModel(val schedulersProvider: SchedulersProvider,
                    val getComics: GetComics) : BaseViewModel() {
    init {
        loadComics()
    }

    fun loadComics() {
        val timestamp = Date().time.toString()
        getComics(timestamp, BuildConfig.API_KEY,
            md5(timestamp + BuildConfig.PRIVATE_KEY + BuildConfig.API_KEY))
            .subscribeOn(schedulersProvider.io())
            .subscribe({},{})
            .autodispose()
    }
}
