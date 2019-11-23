package com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.compose

import androidx.compose.Composable
import androidx.ui.graphics.Color
import androidx.ui.layout.Center
import androidx.ui.layout.FlexColumn
import androidx.ui.material.CircularProgressIndicator

@Composable
fun LoadingScreen() {
    FlexColumn {
        expanded(1f) {
            Center {
                CircularProgressIndicator(color = Color.Cyan)
            }
        }
    }
}