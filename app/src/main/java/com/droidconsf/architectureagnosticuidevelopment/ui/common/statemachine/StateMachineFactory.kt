package com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine

import com.droidconsf.architectureagnosticuidevelopment.ui.ComicbooksViewModel
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

            state<ViewState.Loading> {
                on<Event.System.OnLoadSuccess> { event ->
                    transitionTo(
                        state = ViewState.ShowingComicbooks(
                            ComicbooksViewModel.ComicsContext(
                                comics = event.comics
                            )
                        )
                    )
                }
            }

            state<ViewState.ShowingComicbook> {
                on<Event.View.ComicbookDescriptionExtraLines> { event ->
                    transitionTo(
                        ViewState.ShowingComicbook(
                            comicbookContext = comicbookContext.copy(
                                shouldDisplayShowMoreButton = event.extraLines > 0
                            )
                        )
                    )
                }

                on<Event.View.ShowMoreDescription> { event ->
                    transitionTo(
                        ViewState.ShowingComicbook(
                            comicbookContext = comicbookContext.copy(
                                descriptionExpanded = true
                            )
                        )
                    )
                }
            }

            state<ViewState.ShowingComicbooks> {
                on<Event.View.ShowComicbook> { event ->
                    transitionTo(
                        state = ViewState.ShowingComicbook(
                            comicbookContext.copy(
                                currentDisplayedComic = comicbookContext
                                    .comics.find { it.id.toString() == event.comicbookId }
                            )
                        )
                    )
                }
            }
        }
    }

}