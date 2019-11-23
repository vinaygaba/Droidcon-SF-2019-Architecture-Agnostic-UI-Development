package com.droidconsf.architectureagnosticuidevelopment.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.droidconsf.architectureagnosticuidevelopment.R
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.ComicbookDetailFragment
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.ComicbooksFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ComicbooksFragment.newInstance())
                .commitNow()
        }
    }

    fun goToDetailScreen(comicbookId: String) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container, ComicbookDetailFragment.newInstance(
                    comicbookId = comicbookId
                )
            )
            .commitNow()
    }
}
