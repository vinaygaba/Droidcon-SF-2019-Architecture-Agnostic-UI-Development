package com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine

import com.droidconsf.architectureagnosticuidevelopment.core.api.models.Comic

sealed class Event {

    sealed class View : Event() {
        object LoadComics : View()
        data class ShowComicbook(val comicbookId: String) : View()
        data class ComicbookDescriptionExtraLines(val extraLines: Int) : View()
        object ShowMoreDescription : View()
    }

    sealed class System : Event() {
        data class OnLoadSuccess(val comics: List<Comic>) : System()
        data class OnLoadComicbookDetailSuccess(val comic: Comic) : System()
    }
}