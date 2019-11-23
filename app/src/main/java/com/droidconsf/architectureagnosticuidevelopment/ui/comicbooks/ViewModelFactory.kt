package com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.droidconsf.architectureagnosticuidevelopment.core.rx.SchedulersProvider
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.statemachine.StateMachineFactory
import com.droidconsf.architectureagnosticuidevelopment.core.usecase.GetComics
import javax.inject.Inject

internal class ViewModelFactory @Inject constructor(
    val schedulersProvider: SchedulersProvider,
    val getComics: GetComics,
    val stateMachineFactory: StateMachineFactory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                schedulersProvider,
                getComics,
                stateMachineFactory
            ) as T
        }
        throw IllegalArgumentException("Model class ${modelClass.name} not valid")
    }
}