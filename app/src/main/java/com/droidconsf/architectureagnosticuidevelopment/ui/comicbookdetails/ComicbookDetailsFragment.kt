package com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.droidconsf.architectureagnosticuidevelopment.ArchitectureAgnosticUiApplication
import com.droidconsf.architectureagnosticuidevelopment.R
import com.droidconsf.architectureagnosticuidevelopment.core.api.models.Comic
import com.droidconsf.architectureagnosticuidevelopment.ui.ComicbooksViewModel
import com.droidconsf.architectureagnosticuidevelopment.ui.common.di.ActivityModule
import com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine.Event
import javax.inject.Inject

class ComicbookDetailFragment : Fragment() {

    @Inject
    internal lateinit var viewModel: ComicbooksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_comicbook_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpDagger()

        viewModel.displayComic.observe(this, Observer { it ->
            it?.let { comic ->
                bindViews(comic)
            }
        })

        viewModel.shouldDisplayShowMoreButton.observe(this, Observer {
            it?.let { shouldDisplay ->
                val showMore = view.findViewById<Button>(R.id.showMoreButton)
                if (shouldDisplay) {
                    showMore.visibility = View.VISIBLE
                } else {
                    showMore.visibility = View.GONE
                }
            }
        })

        viewModel.descriptionExpanded.observe(this, Observer {
            it?.let { descriptionExpanded ->
                val comicDescription = view.findViewById<TextView>(R.id.comicDescription)
                if (descriptionExpanded) {
                    comicDescription.maxLines = 0
                } else {
                    comicDescription.maxLines =
                        requireContext().resources.getInteger(R.integer.description_max_lines)
                }
            }
        })
    }

    private fun bindViews(comic: Comic) {
        view?.run {
            val description = findViewById<TextView>(R.id.comicDescription)
            findViewById<TextView>(R.id.comicTitle).text = comic.title
            description.text = comic.description
            Glide.with(this).load(comic.thumbnail).into(findViewById(R.id.comic_image))
            description.doOnLayout {
                descriptionExtraLines(it as TextView)
            }
        }
    }

    private fun descriptionExtraLines(description: TextView) {
        description.layout?.let { layout ->
            viewModel.triggerEvent(
                Event.View.ComicbookDescriptionExtraLines(
                    layout.getEllipsisCount(
                        layout.lineCount - 1
                    )
                )
            )
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
        fun newInstance() = ComicbookDetailFragment()
    }
}