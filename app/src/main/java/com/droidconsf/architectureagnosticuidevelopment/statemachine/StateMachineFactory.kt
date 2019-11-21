package com.droidconsf.architectureagnosticuidevelopment.statemachine

import com.tinder.StateMachine
import javax.inject.Inject

class StateMachineFactory @Inject constructor() {

    fun create(
        initialState: ViewState
    ): StateMachine<ViewState, Event, SideEffect> {
        return StateMachine.create {
            initialState(initialState)

            state<ViewState.Empty> {

                on<Event.View.LoadComics> {
                    dontTransition(
                        sideEffect = SideEffect.LoadComics
                    )
                }

                on<Event.System.OnLoadSuccess> { event ->
                    transitionTo(
                        state = ViewState.ShowingContent(
                            comics = event.comics
                        )
                    )
                }
            }
        }
    }

}