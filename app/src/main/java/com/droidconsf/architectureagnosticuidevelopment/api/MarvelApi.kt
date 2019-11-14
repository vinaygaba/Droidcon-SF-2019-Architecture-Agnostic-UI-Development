package com.droidconsf.architectureagnosticuidevelopment.api

import com.droidconsf.architectureagnosticuidevelopment.api.models.MarvelResponses
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "0b9e363998788b5fbf985b99fdc10e08"

interface MarvelApi {

    @GET("v1/public/comics")
    fun getComics(@Query("ts") ts: String,
                  @Query("apikey") apiKey: String,
                  @Query("hash") hash: String): Single<Response<MarvelResponses>>
}