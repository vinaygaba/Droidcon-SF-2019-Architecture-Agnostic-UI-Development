package com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.compose

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Dp
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.surface.Surface
import androidx.ui.material.themeTextStyle
import androidx.ui.text.style.TextOverflow
import com.droidconsf.architectureagnosticuidevelopment.core.api.models.Comic
import com.droidconsf.architectureagnosticuidevelopment.ui.ComicbooksViewModel
import com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine.Event

@Composable
internal fun ComicsListScreen(viewModel: ComicbooksViewModel, comics: List<Comic>) {
    VerticalScroller {
        FlexColumn {
            expanded(1f) {
                comics.forEach { comic ->
                    Clickable(onClick = onClickAction(viewModel, comic)) {
                        ComicRow(comic)
                    }
                }
            }
        }
    }
}

private fun onClickAction(
    viewModel: ComicbooksViewModel,
    comic: Comic
): () -> Unit = {
    viewModel.triggerEvent(
        Event.View.ShowComicbook(
            comicbookId = comic.id.toString()
        )
    )
}

@Composable
fun ComicRow(comic: Comic) {
    Surface(color = Color(24, 24, 24)) {
        Container(modifier = Spacing(16.dp)) {
            FlexRow {
                inflexible {
                    ComicImage(72.dp, 120.dp)
                }
                expanded(1f) {
                    TitleSubtitleColumn(comic.title, comic.description)
                }
            }
        }
    }
}

@Composable
fun ComicImage(width: Dp, height: Dp) {
    Surface(color = Color(170, 173, 196)) {
        Container(height = height, width = width) {
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