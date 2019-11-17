package com.droidconsf.architectureagnosticuidevelopment.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.droidconsf.architectureagnosticuidevelopment.api.MarvelApi
import com.droidconsf.architectureagnosticuidevelopment.rx.SchedulersProvider
import com.droidconsf.architectureagnosticuidevelopment.viewmodels.MainViewModel
import com.droidconsf.architectureagnosticuidevelopment.viewmodels.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: FragmentActivity) {

    @ActivityScope
    @Provides
    fun provideViewModelFactory(marvelApi: MarvelApi,
                                schedulersProvider: SchedulersProvider): ViewModelFactory {

        return ViewModelFactory(marvelApi, schedulersProvider)
    }

    @ActivityScope
    @Provides
    fun providePhotoListViewModel(viewModelsFactory: ViewModelFactory): MainViewModel {
        return ViewModelProviders.of(activity, viewModelsFactory).get(MainViewModel::class.java)
    }
}