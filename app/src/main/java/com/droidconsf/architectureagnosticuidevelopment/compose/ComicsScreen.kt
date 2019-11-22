package com.droidconsf.architectureagnosticuidevelopment.compose

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview

@Composable
fun ComicsScreen() {
    MaterialTheme {
        VerticalScroller {
            Column(crossAxisSize = LayoutSize.Expand) {
                // TODO(vinay) Replace with data that's passed through the activity
                for (index in 0..5) {
                    ComicRow()
                }
            }
        }
    }
}

@Composable
fun ComicRow(){
    Surface(color = Color(24,24,24)) {
        Row(crossAxisSize = LayoutSize.Expand, modifier = ExpandedWidth) {
            Row(modifier = Spacing(16.dp)) {
                ComicImage()
                Row(modifier = Flexible(1f)) {
                    TitleSubtitleColumn("Title", "Subtitle")
                }
            }
        }
    }
}

@Composable
fun ComicImage() {
    Surface(color = Color(170,173,196)) {
        Container(height = 120.dp, width = 72.dp) {
            // TODO(vinay): Replace with image
        }
    }
}

@Composable
fun TitleSubtitleColumn(title: String, subtitle: String) {
    Column(crossAxisSize = LayoutSize.Expand) {
        Padding(left = 16.dp, right = 16.dp, bottom = 16.dp) {
            Text(title, style = TextStyle(color = Color.White))
        }
        Padding(left = 16.dp, right = 16.dp) {
            Text(subtitle, style = TextStyle(color = Color.White))
        }
    }
}

@Preview
@Composable
fun Preview() {
    ComicsScreen()
}