package com.droidconsf.architectureagnosticuidevelopment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.droidconsf.architectureagnosticuidevelopment.BuildConfig
import com.droidconsf.architectureagnosticuidevelopment.api.models.MarvelResponses
import com.droidconsf.architectureagnosticuidevelopment.rx.SchedulersProvider
import com.droidconsf.architectureagnosticuidevelopment.statemachine.Event
import com.droidconsf.architectureagnosticuidevelopment.statemachine.SideEffect
import com.droidconsf.architectureagnosticuidevelopment.statemachine.StateMachineFactory
import com.droidconsf.architectureagnosticuidevelopment.statemachine.ViewState
import com.droidconsf.architectureagnosticuidevelopment.usecases.GetComics
import com.droidconsf.architectureagnosticuidevelopment.util.md5
import com.tinder.StateMachine
import java.util.Date

class MainViewModel(
    val schedulersProvider: SchedulersProvider,
    val getComics: GetComics
) : BaseViewModel() {

    private val stateMachineFactory = StateMachineFactory()
    private val stateMachine = stateMachineFactory.create(ViewState.Empty)

    private val _state = MutableLiveData<ViewState>()
    private val _sideEffect = MutableLiveData<SideEffect>()
    private val _viewEvent = MutableLiveData<Event>()

    val state: LiveData<ViewState> = _state
    val sideEffect: LiveData<SideEffect> = _sideEffect
    val viewEvent: LiveData<Event> = _viewEvent

    init {
        loadComics()
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
