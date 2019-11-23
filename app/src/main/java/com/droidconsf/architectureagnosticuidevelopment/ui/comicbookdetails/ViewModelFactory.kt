package com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.droidconsf.architectureagnosticuidevelopment.core.rx.SchedulersProvider
import com.droidconsf.architectureagnosticuidevelopment.core.usecase.GetComicDetails
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.statemachine.StateMachineFactory
import javax.inject.Inject

internal class ViewModelFactory @Inject constructor(
    private val schedulersProvider: SchedulersProvider,
    private val getComicDetails: GetComicDetails,
    private val stateMachineFactory: StateMachineFactory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComicbookDetailsViewModel::class.java)) {
            return ComicbookDetailsViewModel(
                schedulersProvider,
                getComicDetails,
                stateMachineFactory
            ) as T
        }
        throw IllegalArgumentException("Model class ${modelClass.name} not valid")
    }
}