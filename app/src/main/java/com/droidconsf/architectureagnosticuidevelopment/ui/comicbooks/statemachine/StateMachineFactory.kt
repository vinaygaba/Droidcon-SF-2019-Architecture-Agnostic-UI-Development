package com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.statemachine

import com.tinder.StateMachine
import javax.inject.Inject

internal class StateMachineFactory @Inject constructor() {

    internal fun create(
        initialState: ViewState
    ): StateMachine<ViewState, Event, SideEffect> {
        return StateMachine.create {
            initialState(initialState)

            state<ViewState.Empty> {
                on<Event.View.LoadComics> {
                    transitionTo(
                        state = ViewState.Loading,
                        sideEffect = SideEffect.LoadComics
                    )
                }
            }

            state<ViewState.ShowingContent> {
                on<Event.View.ComicbookClicked> { event ->
                    transitionTo(
                        state = ViewState.CloseScreen(
                            comicId = event.comicbookId
                        )
                    )
                    transitionTo(
                        state = this
                    )
                }
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