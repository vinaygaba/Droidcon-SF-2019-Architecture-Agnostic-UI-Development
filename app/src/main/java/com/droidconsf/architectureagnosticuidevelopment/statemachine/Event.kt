package com.droidconsf.architectureagnosticuidevelopment.statemachine

import com.droidconsf.architectureagnosticuidevelopment.api.models.Comic

sealed class Event {

    sealed class View : Event() {
        object LoadComics : View()
    }

    sealed class System : Event() {
        data class OnLoadSuccess(val comics: List<Comic>) : System()
    }


}