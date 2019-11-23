package com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.droidconsf.architectureagnosticuidevelopment.BuildConfig
import com.droidconsf.architectureagnosticuidevelopment.core.api.models.MarvelResponses
import com.droidconsf.architectureagnosticuidevelopment.core.rx.SchedulersProvider
import com.droidconsf.architectureagnosticuidevelopment.core.usecase.GetComics
import com.droidconsf.architectureagnosticuidevelopment.core.util.md5
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.statemachine.Event
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.statemachine.SideEffect
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.statemachine.StateMachineFactory
import com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.statemachine.ViewState
import com.droidconsf.architectureagnosticuidevelopment.ui.common.BaseViewModel
import com.droidconsf.architectureagnosticuidevelopment.ui.common.toSingleEvent
import com.tinder.StateMachine
import java.util.Date

internal class MainViewModel(
    val schedulersProvider: SchedulersProvider,
    val getComics: GetComics,
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
}