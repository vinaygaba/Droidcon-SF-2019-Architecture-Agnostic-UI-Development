package com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.compose

import androidx.compose.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.Color
import androidx.ui.layout.Center
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.FlexColumn
import androidx.ui.layout.FlexRow
import androidx.ui.layout.LayoutSize
import androidx.ui.layout.Padding
import androidx.ui.layout.Spacing
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import androidx.ui.material.themeTextStyle
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import com.droidconsf.architectureagnosticuidevelopment.core.api.models.Comic
import com.droidconsf.architectureagnosticuidevelopment.core.api.models.ComicThumbnail
import com.droidconsf.architectureagnosticuidevelopment.ui.ComicbooksViewModel
import com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine.ViewState

@Composable
internal fun ComicsScreen(stateLiveData: LiveData<ViewState>) {
    MaterialTheme {
        VerticalScroller {
            Column(crossAxisSize = LayoutSize.Expand) {
                val state = +observe(stateLiveData)
                when (state) {
                    is ViewState.Loading -> {
                        FlexColumn {
                            expanded(1f) {
                                Center {
                                    CircularProgressIndicator(color = Color.Cyan)
                                }
                            }
                        }
                    }
                    is ViewState.ShowingComicbooks -> {
                        state.comicbookContext.comics.forEach { comic ->
                            ComicRow(comic)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ComicRow(comic: Comic) {
    Surface(color = Color(24, 24, 24)) {
        Container(modifier = Spacing(16.dp)) {
            FlexRow {
                inflexible {
                    ComicImage()
                }
                expanded(1f) {
                    TitleSubtitleColumn(comic.title, comic.description)
                }
            }
        }
    }
}

@Composable
fun ComicImage() {
    Surface(color = Color(170, 173, 196)) {
        Container(height = 120.dp, width = 72.dp) {
            // TODO(vinay): Replace with image
        }
    }
}

@Composable
fun TitleSubtitleColumn(title: String, subtitle: String?) {
    Column {
        Padding(left = 16.dp, right = 16.dp, bottom = 16.dp) {
            val textStyle = (+themeTextStyle { subtitle1 }).copy(color = Color.White)
            Text(title, style = textStyle, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        subtitle?.let { sub ->
            Padding(left = 16.dp, right = 16.dp) {
                val textStyle = (+themeTextStyle { caption }).copy(color = Color.White)
                Text(sub, style = textStyle, maxLines = 3, overflow = TextOverflow.Ellipsis)
            }
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

@Preview
@Composable
fun Preview() {
    val liveData = MutableLiveData<ViewState>()
    liveData.value = ViewState.ShowingComicbooks(
        ComicbooksViewModel.ComicsContext(
            comics = listOf(
                Comic(
                    id = 1, description = "This is dope", issueNumber = 4567,
                    title = "Comic Title that is fairly long to test if maxLines logic is working",
                    thumbnail = ComicThumbnail(
                        extension = "png",
                        path = ""
                    )
                )
            )
        )
    )
    ComicsScreen(
        liveData
    )
}