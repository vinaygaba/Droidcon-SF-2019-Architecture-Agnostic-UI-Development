package com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.statemachine

import com.droidconsf.architectureagnosticuidevelopment.core.api.models.Comic

internal sealed class ViewState {
    object Empty : ViewState()
    object Loading : ViewState()
    data class ShowingContent(
        val comics: List<Comic>
    ) : ViewState()

    data class CloseScreen(
        val comicId: String
    ) : ViewState()
}