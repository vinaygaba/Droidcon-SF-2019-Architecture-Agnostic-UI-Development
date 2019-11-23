package com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine

import com.droidconsf.architectureagnosticuidevelopment.ui.ComicbooksViewModel

internal sealed class ViewState {
    object Empty : ViewState()
    object Loading : ViewState()
    data class ShowingComicbooks(
        val comicbookContext: ComicbooksViewModel.ComicsContext
    ) : ViewState()

    data class ShowingComicbook(
        val comicbookContext: ComicbooksViewModel.ComicsContext
    ) : ViewState()

    object CloseScreen : ViewState()
}