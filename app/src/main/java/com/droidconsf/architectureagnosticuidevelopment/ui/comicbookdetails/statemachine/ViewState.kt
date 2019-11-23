package com.droidconsf.architectureagnosticuidevelopment.ui.comicbookdetails.statemachine

import com.droidconsf.architectureagnosticuidevelopment.core.api.models.Comic

internal sealed class ViewState {
    object Empty : ViewState()
    object Loading : ViewState()
    data class ShowingContent(val comics: Comic) : ViewState()
}