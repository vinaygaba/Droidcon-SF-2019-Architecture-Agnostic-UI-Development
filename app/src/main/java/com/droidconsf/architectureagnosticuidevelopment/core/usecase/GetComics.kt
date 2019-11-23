package com.droidconsf.architectureagnosticuidevelopment.core.usecase

import com.droidconsf.architectureagnosticuidevelopment.core.api.MarvelApi
import javax.inject.Inject

class GetComics @Inject constructor(private val marvelApi: MarvelApi) {
    operator fun invoke(timeStamp: String, apiKey: String, hash: String) =
        marvelApi.getComics(timestamp = timeStamp, apiKey = apiKey, hash = hash)
}