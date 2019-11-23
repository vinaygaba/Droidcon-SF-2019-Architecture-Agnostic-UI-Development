package com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.statemachine


internal sealed class SideEffect {
    data class LoadComicDetails(val comicbookId: String) : SideEffect()
}