package com.droidconsf.architectureagnosticuidevelopment.ui.common.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {

    @Singleton
    @Provides
    fun providesApplication(): Application {
        return application
    }

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application
    }
}