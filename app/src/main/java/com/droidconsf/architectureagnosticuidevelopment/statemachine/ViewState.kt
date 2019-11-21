package com.droidconsf.architectureagnosticuidevelopment.statemachine

import com.droidconsf.architectureagnosticuidevelopment.api.models.Comic

sealed class ViewState {
    object Empty : ViewState()
    object Loading : ViewState()
    data class ShowingContent(val comics: List<Comic>) : ViewState()
}