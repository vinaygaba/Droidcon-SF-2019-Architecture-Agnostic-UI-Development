package com.droidconsf.architectureagnosticuidevelopment.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface AppComponent {
    fun activitySubComponent(perActivityModule: ActivityModule): ActivitySubComponent
}