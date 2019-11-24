package com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine

import com.droidconsf.architectureagnosticuidevelopment.ui.ComicbooksViewModel

internal sealed class ViewState {
    object Empty : ViewState()
    object Loading : ViewState()
    data class ShowingComicBookList(
        val comicbookContext: ComicbooksViewModel.ComicsContext
    ) : ViewState()

    data class ShowingComicBook(
        val comicbookContext: ComicbooksViewModel.ComicsContext
    ) : ViewState()

    object CloseScreen : ViewState()
}