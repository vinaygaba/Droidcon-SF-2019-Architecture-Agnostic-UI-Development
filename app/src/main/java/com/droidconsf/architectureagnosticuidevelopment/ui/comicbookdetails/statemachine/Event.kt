package com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.statemachine

import com.droidconsf.architectureagnosticuidevelopment.core.api.models.Comic

internal sealed class Event {
    sealed class View : Event() {
        data class LoadComicDetails(val comicId: String) : View()
    }

    sealed class System : Event() {
        data class OnLoadSuccess(val comics: Comic) : System()
    }
}