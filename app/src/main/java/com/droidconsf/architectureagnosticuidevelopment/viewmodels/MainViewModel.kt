package com.droidconsf.architectureagnosticuidevelopment.viewmodels

import com.droidconsf.architectureagnosticuidevelopment.BuildConfig
import com.droidconsf.architectureagnosticuidevelopment.api.MarvelApi
import com.droidconsf.architectureagnosticuidevelopment.rx.SchedulersProvider
import com.droidconsf.architectureagnosticuidevelopment.util.md5
import java.util.Date

// TODO(vinay) Replace this with a usecase which in turn contains a repository which references the
// MarvelApi
class MainViewModel(val marvelApi: MarvelApi,
                    val schedulersProvider: SchedulersProvider) : BaseViewModel() {
    init {
        loadComics()
    }

    fun loadComics() {
        val timestamp = Date().time.toString()
        marvelApi.getComics(timestamp, BuildConfig.API_KEY,
            md5(timestamp + BuildConfig.PRIVATE_KEY + BuildConfig.API_KEY))
            .subscribeOn(schedulersProvider.io())
            .subscribe({},{})
            .autodispose()
    }
}
