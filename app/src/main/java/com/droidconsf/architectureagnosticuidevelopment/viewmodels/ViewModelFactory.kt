package com.droidconsf.architectureagnosticuidevelopment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.droidconsf.architectureagnosticuidevelopment.api.MarvelApi
import com.droidconsf.architectureagnosticuidevelopment.rx.SchedulersProvider
import com.droidconsf.architectureagnosticuidevelopment.usecases.GetComics
import javax.inject.Inject

class ViewModelFactory @Inject constructor(val schedulersProvider: SchedulersProvider,
                                           val getComics: GetComics)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(schedulersProvider, getComics) as T
        }
        throw IllegalArgumentException("Model class ${modelClass.name} not valid")
    }
}