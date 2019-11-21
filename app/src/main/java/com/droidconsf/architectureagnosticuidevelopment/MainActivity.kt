package com.droidconsf.architectureagnosticuidevelopment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.droidconsf.architectureagnosticuidevelopment.ui.main.ComicbooksFragment

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
}
