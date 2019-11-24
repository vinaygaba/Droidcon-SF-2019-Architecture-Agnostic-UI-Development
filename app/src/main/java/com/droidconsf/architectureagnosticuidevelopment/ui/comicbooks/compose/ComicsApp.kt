package com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.compose

import androidx.compose.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.ui.material.MaterialTheme
import androidx.ui.material.themeColor
import com.droidconsf.architectureagnosticuidevelopment.ui.ComicbooksViewModel
import com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine.ViewState

@Composable
internal fun ComicsApp(
    viewModel: ComicbooksViewModel
) {
    MaterialTheme {
        val viewState = +observe(viewModel.state)
        val displayComic = +observe(viewModel.displayComic)
        val descriptionExpanded = +observe(viewModel.descriptionExpanded)
        val primaryColor = +themeColor { primary }
        when (viewState) {
            is ViewState.Loading -> LoadingScreen(primaryColor)
            is ViewState.ShowingComicBookList -> ComicsListScreen(viewModel,
                viewState.comicbookContext.comics)
            is ViewState.ShowingComicBook -> ComicDetailScreen(viewModel, displayComic,
                descriptionExpanded)
        }
    }
}

/**
 * This should be provided by compose itself but seems like its not included yet. An example was
 * shown in Leland's talk at AndroidDevSummit 2019 -https://www.youtube.com/watch?v=Q9MtlmmN4Q0
 * Took the below code from this blogpost -
 * https://medium.com/swlh/android-mvi-with-jetpack-compose-b0890f5156ac
 */
fun <T> observe(data: LiveData<T>) = effectOf<T?> {
    val result = +state<T?> { data.value }
    val observer = +memo { Observer<T> { result.value = it } }

    +onCommit(data) {
        data.observeForever(observer)
        onDispose { data.removeObserver(observer) }
    }
    result.value
}