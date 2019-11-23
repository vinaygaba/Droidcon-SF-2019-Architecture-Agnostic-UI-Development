package com.droidconsf.architectureagnosticuidevelopment.ui.common.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.droidconsf.architectureagnosticuidevelopment.core.rx.SchedulersProvider
import com.droidconsf.architectureagnosticuidevelopment.core.usecase.GetComics
import com.droidconsf.architectureagnosticuidevelopment.ui.ComicbooksViewModel
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.ViewModelFactory
import com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine.StateMachineFactory
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
    internal fun providePhotoListViewModel(viewModelsFactory: ViewModelFactory): ComicbooksViewModel {
        return ViewModelProviders.of(activity, viewModelsFactory).get(ComicbooksViewModel::class.java)
    }
}