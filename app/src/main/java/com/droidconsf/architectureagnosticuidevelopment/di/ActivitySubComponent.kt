package com.droidconsf.architectureagnosticuidevelopment.di

import com.droidconsf.architectureagnosticuidevelopment.MainActivity
import com.droidconsf.architectureagnosticuidevelopment.ui.main.ComicbooksFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivitySubComponent {
    fun inject(activity: MainActivity)

    fun inject(fragment: ComicbooksFragment)
}