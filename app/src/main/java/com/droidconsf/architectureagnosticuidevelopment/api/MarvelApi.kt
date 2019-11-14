package com.droidconsf.architectureagnosticuidevelopment.api

import com.droidconsf.architectureagnosticuidevelopment.api.models.Comics
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

const val API_KEY = "0b9e363998788b5fbf985b99fdc10e08"

interface MarvelApi {
    @GET("GET /v1/public/comics?api_key=$API_KEY")
    fun getComics(): Single<Response<Comics>>
}