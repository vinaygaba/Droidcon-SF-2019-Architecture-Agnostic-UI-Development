package com.droidconsf.architectureagnosticuidevelopment.usecases

import com.droidconsf.architectureagnosticuidevelopment.api.MarvelApi
import javax.inject.Inject

class GetComics @Inject constructor(val marvelApi: MarvelApi) {
    operator fun invoke(timeStamp: String, apiKey: String, hash: String) =
        marvelApi.getComics(timestamp = timeStamp, apiKey = apiKey, hash = hash)
}