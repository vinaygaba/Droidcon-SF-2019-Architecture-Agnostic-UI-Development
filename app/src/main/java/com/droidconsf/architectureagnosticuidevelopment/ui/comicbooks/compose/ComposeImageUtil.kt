package com.droidconsf.architectureagnosticuidevelopment.ui.comicbooks.compose

import com.droidconsf.architectureagnosticuidevelopment.R

/**
 * Using random image res id's because there is no clean way to load network images in compose.
 * The caveat is that the images change when the compose components are redrawn since this method
 * returns a random image res id. Even the JetNews app is using static images so I just went with t
 * hat to keep things simple.
 * JetNews sample app - https://github.com/android/compose-samples/tree/master/JetNews
 */
fun getRandomImageResId(): Int {
    return listOf<Int>(R.drawable.marvel1, R.drawable.marvel2, R.drawable.marvel3, R.drawable.marvel4,
        R.drawable.marvel5).shuffled().take(1)[0]
}