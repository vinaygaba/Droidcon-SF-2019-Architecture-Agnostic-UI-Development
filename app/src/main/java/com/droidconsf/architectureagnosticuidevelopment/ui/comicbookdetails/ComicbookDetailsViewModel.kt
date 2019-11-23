package com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.droidconsf.architectureagnosticuidevelopment.BuildConfig
import com.droidconsf.architectureagnosticuidevelopment.core.api.models.MarvelResponses
import com.droidconsf.architectureagnosticuidevelopment.core.rx.SchedulersProvider
import com.droidconsf.architectureagnosticuidevelopment.core.usecase.GetComicDetails
import com.droidconsf.architectureagnosticuidevelopment.core.util.md5
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.statemachine.Event
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.statemachine.SideEffect
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.statemachine.StateMachineFactory
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.statemachine.ViewState
import com.droidconsf.architectureagnosticuidevelopment.ui.common.BaseViewModel
import com.droidconsf.architectureagnosticuidevelopment.ui.common.toSingleEvent
import com.tinder.StateMachine
import java.util.Date

internal class ComicbookDetailsViewModel(
    val schedulersProvider: SchedulersProvider,
    val getComicbookDetails: GetComicDetails,
    stateMachineFactory: StateMachineFactory
) : BaseViewModel() {

    private val stateMachine = stateMachineFactory.create(ViewState.Empty)

    private val _state = MutableLiveData<ViewState>()
    private val _sideEffect = MutableLiveData<SideEffect>()
    private val _viewEvent = MutableLiveData<Event>()

    val state: LiveData<ViewState> = _state
    val sideEffect: LiveData<SideEffect> = _sideEffect
    val viewEvent: LiveData<Event> = _viewEvent.toSingleEvent()

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
            is SideEffect.LoadComicDetails -> loadComicbookDetails(sideEffect.comicbookId)
        }
    }

    private fun loadComicbookDetails(comicbookId: String) {
        val timestamp = Date().time.toString()
        getComicbookDetails(
            timeStamp = timestamp,
            apiKey = BuildConfig.API_KEY,
            hash = md5(timestamp + BuildConfig.PRIVATE_KEY + BuildConfig.API_KEY),
            comicbookId = comicbookId
        )
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe(
                { marvelResponse ->
                    triggerEvent(
                        event = Event.System.OnLoadSuccess(
                            comics = (marvelResponse.body() as MarvelResponses).data.comicsList.first()
                        )
                    )
                },
                {}
            )
            .autodispose()
    }

}