package com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidconsf.architectureagnosticuidevelopment.ArchitectureAgnosticUiApplication
import com.droidconsf.architectureagnosticuidevelopment.R
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.statemachine.Event
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.statemachine.ViewState
import com.droidconsf.architectureagnosticuidevelopment.ui.common.EndlessRecyclerOnScrollListener
import com.droidconsf.architectureagnosticuidevelopment.ui.common.di.ActivityModule
import com.droidconsf.architectureagnosticuidevelopment.ui.main.MainActivity
import javax.inject.Inject

class ComicbooksFragment : Fragment() {

    companion object {
        fun newInstance() =
            ComicbooksFragment()
    }

    @Inject
    internal lateinit var viewModel: MainViewModel

    private lateinit var rvComicbooks: RecyclerView
    private lateinit var pbCharacter: ProgressBar
    private val comicbooksAdapter =
        ComicbooksAdapter()

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
                is ViewState.ShowingContent -> {
                    comicbooksAdapter.updateData(state.comics)
                    pbCharacter.visibility = View.GONE
                }
                is ViewState.Loading -> {
                    pbCharacter.visibility = View.VISIBLE
                }

                is ViewState.CloseScreen -> {
                    // Forgive us for the lack of fanciness in this communication
                    (requireActivity() as MainActivity).goToDetailScreen(state.comicId)
                }
            }
        })
    }

    private fun setupViews() {
        view?.let {
            pbCharacter = it.findViewById(R.id.character_progress_bar)

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
