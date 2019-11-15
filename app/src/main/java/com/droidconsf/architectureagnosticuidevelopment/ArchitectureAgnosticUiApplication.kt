package com.droidconsf.architectureagnosticuidevelopment

import android.app.Application
import com.droidconsf.architectureagnosticuidevelopment.di.AppComponent
import com.droidconsf.architectureagnosticuidevelopment.di.AppModule
import com.droidconsf.architectureagnosticuidevelopment.di.DaggerAppComponent
import com.droidconsf.architectureagnosticuidevelopment.di.NetModule


class ArchitectureAgnosticUiApplication: Application() {
    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule())
            .build()
    }
}