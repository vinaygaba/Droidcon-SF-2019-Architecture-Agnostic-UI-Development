package com.droidconsf.architectureagnosticuidevelopment.core.usecase

import com.droidconsf.architectureagnosticuidevelopment.core.api.MarvelApi
import javax.inject.Inject

class GetComicDetails @Inject constructor(private val marvelApi: MarvelApi) {
    operator fun invoke(comicbookId: String, timeStamp: String, apiKey: String, hash: String) =
        marvelApi.getComicbookDetail(
            comicId = comicbookId,
            timestamp = timeStamp,
            apiKey = apiKey,
            hash = hash
        )
}