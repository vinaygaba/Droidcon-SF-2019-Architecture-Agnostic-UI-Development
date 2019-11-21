package com.droidconsf.architectureagnosticuidevelopment.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.droidconsf.architectureagnosticuidevelopment.rx.SchedulersProvider
import com.droidconsf.architectureagnosticuidevelopment.statemachine.StateMachineFactory
import com.droidconsf.architectureagnosticuidevelopment.usecases.GetComics
import com.droidconsf.architectureagnosticuidevelopment.viewmodels.MainViewModel
import com.droidconsf.architectureagnosticuidevelopment.viewmodels.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: FragmentActivity) {

    @ActivityScope
    @Provides
    fun provideViewModelFactory(
        getComics: GetComics,
        schedulersProvider: SchedulersProvider,
        stateMachineFactory: StateMachineFactory
    ): ViewModelFactory {
        return ViewModelFactory(schedulersProvider, getComics, stateMachineFactory)
    }

    @ActivityScope
    @Provides
    fun providePhotoListViewModel(viewModelsFactory: ViewModelFactory): MainViewModel {
        return ViewModelProviders.of(activity, viewModelsFactory).get(MainViewModel::class.java)
    }
}