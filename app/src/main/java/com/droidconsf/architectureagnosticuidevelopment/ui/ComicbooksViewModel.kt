package com.droidconsf.architectureagnosticuidevelopment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.droidconsf.architectureagnosticuidevelopment.BuildConfig
import com.droidconsf.architectureagnosticuidevelopment.core.api.models.Comic
import com.droidconsf.architectureagnosticuidevelopment.core.api.models.MarvelResponses
import com.droidconsf.architectureagnosticuidevelopment.core.rx.SchedulersProvider
import com.droidconsf.architectureagnosticuidevelopment.core.usecase.GetComics
import com.droidconsf.architectureagnosticuidevelopment.core.util.md5
import com.droidconsf.architectureagnosticuidevelopment.ui.common.BaseViewModel
import com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine.Event
import com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine.SideEffect
import com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine.StateMachineFactory
import com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine.ViewState
import com.droidconsf.architectureagnosticuidevelopment.ui.common.toSingleEvent
import com.tinder.StateMachine
import java.util.Date

internal class ComicbooksViewModel(
    private val schedulersProvider: SchedulersProvider,
    private val getComics: GetComics,
    stateMachineFactory: StateMachineFactory
) : BaseViewModel() {

    private val stateMachine = stateMachineFactory.create(ViewState.Empty)

    private val _state = MutableLiveData<ViewState>()
    private val _sideEffect = MutableLiveData<SideEffect>()
    private val _viewEvent = MutableLiveData<Event>()

    private var currentDisplayedComic: Comic? = null

    val state: LiveData<ViewState> = _state
    val sideEffect: LiveData<SideEffect> = _sideEffect
    private val viewEvent: LiveData<Event> = _viewEvent.toSingleEvent()

    // Specialized LiveData to remove verification logic from UI
    val displayComic = Transformations.map(state) { viewState ->
        if (viewState is ViewState.ShowingComicBook &&
            viewState.comicbookContext.currentDisplayedComic != currentDisplayedComic
        ) {
            currentDisplayedComic = viewState.comicbookContext.currentDisplayedComic
            currentDisplayedComic
        } else {
            null
        }
    }

    val shouldDisplayShowMoreButton = Transformations.map(state) { viewState ->
        if (viewState is ViewState.ShowingComicBook) {
            viewState.comicbookContext.shouldDisplayShowMoreButton
        } else {
            null
        }
    }
    val descriptionExpanded = Transformations.map(state) { viewState ->
        if (viewState is ViewState.ShowingComicBook) {
            viewState.comicbookContext.descriptionExpanded
        } else {
            null
        }
    }

    fun triggerEvent(event: Event) {
        _viewEvent.value = event
        val transition = stateMachine.transition(event)

        if (transition is StateMachine.Transition.Valid) {
            _state.value = stateMachine.state

            transition.sideEffect?.let {
                _sideEffect.value = it
                handleSideEffect(it)
            }
        }
    }

    private fun handleSideEffect(sideEffect: SideEffect) {
        when (sideEffect) {
            SideEffect.LoadComics -> loadComics()
        }
    }

    private fun loadComics() {
        val timestamp = Date().time.toString()
        getComics(
            timestamp, BuildConfig.API_KEY,
            md5(timestamp + BuildConfig.PRIVATE_KEY + BuildConfig.API_KEY)
        )
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe(
                { marvelResponse ->
                    triggerEvent(
                        event = Event.System.OnLoadSuccess(
                            comics = (marvelResponse.body() as MarvelResponses).data.comicsList
                        )
                    )
                },
                {}
            )
            .autodispose()
    }

    data class ComicsContext(
        val shouldNavigate: Boolean = true,
        val comics: List<Comic>,
        val currentDisplayedComic: Comic? = null,
        val shouldDisplayShowMoreButton: Boolean = false,
        val descriptionExpanded: Boolean = false
    )
}