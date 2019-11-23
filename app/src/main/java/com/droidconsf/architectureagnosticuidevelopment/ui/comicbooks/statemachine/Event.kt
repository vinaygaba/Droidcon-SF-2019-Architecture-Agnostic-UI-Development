package com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.statemachine

import com.droidconsf.architectureagnosticuidevelopment.core.api.models.Comic

internal sealed class Event {

    sealed class View : Event() {
        object LoadComics : View()
        data class ComicbookClicked(val comicbookId: String): View()
    }

    sealed class System : Event() {
        data class OnLoadSuccess(val comics: List<Comic>) : System()
    }
}