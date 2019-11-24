package com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidconsf.architectureagnosticuidevelopment.ArchitectureAgnosticUiApplication
import com.droidconsf.architectureagnosticuidevelopment.R
import com.droidconsf.architectureagnosticuidevelopment.ui.ComicbooksViewModel
import com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine.Event
import com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine.ViewState
import com.droidconsf.architectureagnosticuidevelopment.ui.common.EndlessRecyclerOnScrollListener
import com.droidconsf.architectureagnosticuidevelopment.ui.common.di.ActivityModule
import javax.inject.Inject

class ComicbooksFragment : Fragment() {

    companion object {
        fun newInstance() =
            ComicbooksFragment()
    }

    @Inject
    internal lateinit var viewModel: ComicbooksViewModel

    private lateinit var rvComicbooks: RecyclerView
    private val comicbooksAdapter = ComicbooksAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_comicbook_list, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            (it.application as ArchitectureAgnosticUiApplication).appComponent
                ?.activitySubComponent(ActivityModule(it))
                ?.inject(this)
        }

        setupViews()

        viewModel.triggerEvent(Event.View.LoadComics)
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is ViewState.ShowingComicBookList -> {
                    comicbooksAdapter.updateData(state.comicbookContext.comics)
                }
            }
        })

        comicbooksAdapter.onComicbookClick = { comicbookId ->
            viewModel.triggerEvent(
                Event.View.ShowComicbook(
                    comicbookId = comicbookId
                )
            )
        }
    }

    private fun setupViews() {
        view?.let {
            // RecyclerView
            rvComicbooks = it.findViewById(R.id.rv_comicbook)
            rvComicbooks.layoutManager = LinearLayoutManager(context)
            rvComicbooks.adapter = comicbooksAdapter
            rvComicbooks.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
                override fun onLoadMore() {
                    //  no op yet
                }
            })
        }
    }
}
