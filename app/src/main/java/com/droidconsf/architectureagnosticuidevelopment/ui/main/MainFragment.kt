package com.droidconsf.architectureagnosticuidevelopment.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.droidconsf.architectureagnosticuidevelopment.ArchitectureAgnosticUiApplication
import com.droidconsf.architectureagnosticuidevelopment.R
import com.droidconsf.architectureagnosticuidevelopment.di.ActivityModule
import com.droidconsf.architectureagnosticuidevelopment.statemachine.Event
import com.droidconsf.architectureagnosticuidevelopment.viewmodels.MainViewModel
import javax.inject.Inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        val textView = view.findViewById<TextView>(R.id.message)
        textView.setOnClickListener {
            viewModel.triggerEvent(Event.View.LoadComics)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            (it.application as ArchitectureAgnosticUiApplication).appComponent
            ?.activitySubComponent(ActivityModule(it))
            ?.inject(this)
        }
    }
}
