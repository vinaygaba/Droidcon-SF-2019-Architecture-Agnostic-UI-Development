package com.droidconsf.architectureagnosticuidevelopment.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.ui.core.setContent
import com.droidconsf.architectureagnosticuidevelopment.compose.ComicsScreen

class ComposeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComicsScreen()
        }
    }
}
