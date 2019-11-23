package com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.statemachine

import com.tinder.StateMachine
import javax.inject.Inject

internal class StateMachineFactory @Inject constructor() {

    internal fun create(
        initialState: ViewState
    ): StateMachine<ViewState, Event, SideEffect> {
        return StateMachine.create {
            initialState(initialState)

            state<ViewState.Empty> {
                on<Event.View.LoadComicDetails> { event ->
                    transitionTo(
                        state = ViewState.Loading,
                        sideEffect = SideEffect.LoadComicDetails(
                            comicbookId = event.comicId
                        )
                    )
                }
            }

            state<ViewState.ShowingContent> {
                //no op
            }

            state<ViewState.Loading> {
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