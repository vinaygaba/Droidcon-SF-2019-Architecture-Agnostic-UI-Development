package com.droidconsf.architectureagnosticuidevelopment.ui.common.di

import com.droidconsf.architectureagnosticuidevelopment.ui.main.MainActivity
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.ComicbookDetailFragment
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.ComicbooksFragment
import com.droidconsf.architectureagnosticuidevelopment.ui.main.ComposeActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivitySubComponent {
    fun inject(activity: MainActivity)

    fun inject(activity: ComposeActivity)

    fun inject(fragment: ComicbooksFragment)

    fun inject(fragment: ComicbookDetailFragment)
}