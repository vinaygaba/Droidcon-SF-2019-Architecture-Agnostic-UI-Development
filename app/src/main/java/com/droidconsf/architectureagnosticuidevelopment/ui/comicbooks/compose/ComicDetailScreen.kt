package com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.compose

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.shape.RectangleShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.ButtonStyle
import androidx.ui.material.themeTextStyle
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import com.droidconsf.architectureagnosticuidevelopment.core.api.models.Comic
import com.droidconsf.architectureagnosticuidevelopment.ui.ComicbooksViewModel
import com.droidconsf.architectureagnosticuidevelopment.ui.common.statemachine.Event

@Composable
internal fun ComicDetailScreen(
    viewModel: ComicbooksViewModel,
    displayState: Comic?,
    descriptionExpanded: Boolean?
) {
    displayState?.let { state ->
        Container(modifier = Spacing(16.dp), alignment = Alignment.TopCenter) {
            FlexRow {
                inflexible {
                    ComicImage(122.dp, 170.dp)
                }
                expanded(1f) {
                    ComicDetailColumn(state.title, state.description, descriptionExpanded, viewModel)
                }
            }
        }
    }
}

@Composable
internal fun ComicDetailColumn(
    title: String,
    subtitle: String?,
    descriptionExpanded: Boolean?,
    viewModel: ComicbooksViewModel
) {

    val maxLines = if (descriptionExpanded == true) 12 else 3
    Column {
        Padding(left = 16.dp, right = 16.dp, bottom = 16.dp) {
            val textStyle = (+themeTextStyle { subtitle1 }).copy(color = Color.Black)
            Text(title, style = textStyle, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        subtitle?.let { sub ->
            Padding(left = 16.dp, right = 16.dp) {
                val textStyle = (+themeTextStyle { caption }).copy(color = Color.Black)
                Text(sub, style = textStyle, maxLines = maxLines, overflow = TextOverflow.Ellipsis)
            }
            if (descriptionExpanded != true) {
                val buttonStyle = (+themeTextStyle { h6 }).copy(color = Color.Black)
                Center {
                    Padding(left = 16.dp, right = 16.dp) {
                        Clickable(onClick = {
                            viewModel.triggerEvent(Event.View.ShowMoreDescription)
                        }) {
                            Text(
                                text = "SHOW MORE",
                                style = buttonStyle
                            )
                        }
                    }
                }
            }
        }
    }
}