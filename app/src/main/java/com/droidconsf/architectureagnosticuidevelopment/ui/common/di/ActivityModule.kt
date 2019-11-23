package com.droidconsf.architectureagnosticuidevelopment.ui.common.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.droidconsf.architectureagnosticuidevelopment.core.rx.SchedulersProvider
import com.droidconsf.architectureagnosticuidevelopment.core.usecase.GetComicDetails
import com.droidconsf.architectureagnosticuidevelopment.core.usecase.GetComics
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.ComicbookDetailsViewModel
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.MainViewModel
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.ViewModelFactory
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.statemachine.StateMachineFactory
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.statemachine.StateMachineFactory as ComicbookDetailStateMachineFactory
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.ViewModelFactory as ComicbookDetailsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: FragmentActivity) {

    @ActivityScope
    @Provides
    internal fun provideViewModelFactory(
        getComics: GetComics,
        schedulersProvider: SchedulersProvider,
        stateMachineFactory: StateMachineFactory
    ): ViewModelFactory {
        return ViewModelFactory(
            schedulersProvider,
            getComics,
            stateMachineFactory
        )
    }

    @ActivityScope
    @Provides
    internal fun provideComicBookDetailViewModelFactory(
        getComicDetails: GetComicDetails,
        schedulersProvider: SchedulersProvider,
        stateMachineFactory: ComicbookDetailStateMachineFactory
    ): ComicbookDetailsViewModelFactory {
        return ComicbookDetailsViewModelFactory(
            schedulersProvider = schedulersProvider,
            getComicDetails = getComicDetails,
            stateMachineFactory = stateMachineFactory
        )
    }


    @ActivityScope
    @Provides
    internal fun providePhotoListViewModel(viewModelsFactory: ViewModelFactory): MainViewModel {
        return ViewModelProviders.of(activity, viewModelsFactory).get(MainViewModel::class.java)
    }

    @ActivityScope
    @Provides
    internal fun provideComicbookDetailViewModel(viewModelsFactory: ComicbookDetailsViewModelFactory): ComicbookDetailsViewModel {
        return ViewModelProviders.of(activity, viewModelsFactory).get(ComicbookDetailsViewModel::class.java)
    }
}