package com.droidconsf.architectureagnosticuidevelopment.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.ui.core.setContent
import com.droidconsf.architectureagnosticuidevelopment.ArchitectureAgnosticUiApplication
import com.droidconsf.architectureagnosticuidevelopment.compose.ComicsScreen
import com.droidconsf.architectureagnosticuidevelopment.di.ActivityModule
import com.droidconsf.architectureagnosticuidevelopment.statemachine.Event
import com.droidconsf.architectureagnosticuidevelopment.viewmodels.MainViewModel
import javax.inject.Inject

class ComposeActivity : AppCompatActivity() {
    @Inject
    internal lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
        viewModel.triggerEvent(Event.View.LoadComics)
        viewModel.state.observe(this, Observer { state ->
            // TODO(vinay) Improve this to avoid calling this multiple times and use the
            // state observer that compose is capable of doing(I think!)
            setContent {
                ComicsScreen(state)
            }
        })
    }

    private fun initDagger() {
        (application as ArchitectureAgnosticUiApplication).appComponent
            ?.activitySubComponent(ActivityModule(this))
            ?.inject(this)
    }
}
