package com.droidconsf.architectureagnosticuidevelopment.ui.common.di

import com.droidconsf.architectureagnosticuidevelopment.core.di.NetModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface AppComponent {
    fun activitySubComponent(perActivityModule: ActivityModule): ActivitySubComponent
}