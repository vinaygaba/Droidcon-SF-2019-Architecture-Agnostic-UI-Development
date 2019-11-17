package com.droidconsf.architectureagnosticuidevelopment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.droidconsf.architectureagnosticuidevelopment.api.MarvelApi
import com.droidconsf.architectureagnosticuidevelopment.rx.SchedulersProvider
import javax.inject.Inject

class ViewModelFactory @Inject constructor(val marvelApi: MarvelApi,
                                           val schedulersProvider: SchedulersProvider)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(marvelApi, schedulersProvider) as T
        }
        throw IllegalArgumentException("Model class ${modelClass.name} not valid")
    }
}