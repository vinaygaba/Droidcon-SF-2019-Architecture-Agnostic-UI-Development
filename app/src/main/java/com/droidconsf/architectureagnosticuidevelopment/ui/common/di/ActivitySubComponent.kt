package com.droidconsf.architectureagnosticuidevelopment.ui.common.di

import com.droidconsf.architectureagnosticuidevelopment.ui.MainActivity
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.ComicbookDetailFragment
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.ComicbooksFragment
import com.droidconsf.architectureagnosticuidevelopment.ui.ComposeActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivitySubComponent {
    fun inject(activity: MainActivity)

    fun inject(activity: ComposeActivity)

    fun inject(fragment: ComicbooksFragment)

    fun inject(fragment: ComicbookDetailFragment)
}