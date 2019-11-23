package com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.droidconsf.architectureagnosticuidevelopment.ArchitectureAgnosticUiApplication
import com.droidconsf.architectureagnosticuidevelopment.R
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.statemachine.Event
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.ComicbooksFragment
import com.droidconsf.architectureagnosticuidevelopment.ui.common.di.ActivityModule
import javax.inject.Inject

private const val EXTRA_COMICBOOK_ID = "extra_comic_book_id"

class ComicbookDetailFragment : Fragment() {

    @Inject
    internal lateinit var viewModel: ComicbookDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comicbook_details, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpDagger()

        arguments?.getString(EXTRA_COMICBOOK_ID)?.let { comicId ->
            viewModel.triggerEvent(Event.View.LoadComicDetails(comicId = comicId))
        }
    }

    private fun setUpDagger() {
        activity?.let {
            (it.application as ArchitectureAgnosticUiApplication).appComponent
                ?.activitySubComponent(ActivityModule(it))
                ?.inject(this)
        }
    }

    companion object {
        fun newInstance(comicbookId: String) = ComicbooksFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_COMICBOOK_ID, comicbookId)
            }
        }
    }

}