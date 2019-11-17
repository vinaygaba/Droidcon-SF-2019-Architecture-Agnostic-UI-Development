package com.droidconsf.architectureagnosticuidevelopment.api

import com.droidconsf.architectureagnosticuidevelopment.api.models.MarvelResponses
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("v1/public/comics")
    fun getComics(@Query("ts") timestamp: String,
                  @Query("apikey") apiKey: String,
                  @Query("hash") hash: String): Single<Response<MarvelResponses>>
}