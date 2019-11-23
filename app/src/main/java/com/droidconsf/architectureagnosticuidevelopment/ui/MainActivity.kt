package com.droidconsf.architectureagnosticuidevelopment.ui

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.droidconsf.architectureagnosticuidevelopment.ArchitectureAgnosticUiApplication
import com.droidconsf.architectureagnosticuidevelopment.R
import com.droidconsf.architectureagnosticuidevelopment.core.util.hide
import com.droidconsf.architectureagnosticuidevelopment.core.util.show
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.ComicbookDetailFragment
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.ComicbooksFragment
import com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine.Event
import com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine.ViewState
import com.droidconsf.architectureagnosticuidevelopment.ui.common.di.ActivityModule
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModel: ComicbooksViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initDagger()
        initViews()

        viewModel.triggerEvent(Event.View.LoadComics)

        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is ViewState.Loading -> {
                    progressBar.show()
                }
                is ViewState.ShowingComicbooks -> {
                    if (savedInstanceState == null && state.comicbookContext.shouldNavigate) {
                        progressBar.hide()
                        showComicbooksScreen()
                    }
                }
                is ViewState.ShowingComicbook -> {
                    if (savedInstanceState == null && state.comicbookContext.shouldNavigate) {
                        progressBar.hide()
                        showDetailScreen()
                    }
                }
            }
        })
    }

    private fun showComicbooksScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ComicbooksFragment.newInstance())
            .commitNow()
    }


    private fun showDetailScreen() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container, ComicbookDetailFragment.newInstance()
            )
            .commitNow()
    }

    private fun initViews() {
        progressBar = findViewById(R.id.recycler_view_progress_bar)
    }

    private fun initDagger() {
        (application as ArchitectureAgnosticUiApplication).appComponent
            ?.activitySubComponent(ActivityModule(this))
            ?.inject(this)
    }
}
