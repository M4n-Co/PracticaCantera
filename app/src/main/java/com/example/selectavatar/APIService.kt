package com.example.selectavatar

import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("1?nameOptions=starwarsFirstNames")
    suspend fun userRandom(): Response<List<String>>
}