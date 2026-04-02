package com.example.apipractice.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MyAPIService {
    @GET("mlb/v1/players")
    suspend fun getPlayers(
        @Header("Authorization") apiKey: String,
        @Query("search") search: String? = null,
        @Query("per_page") perPage:Int = 25
    ): String

    @GET("mlb/v1/players/{id}")
    suspend fun getPlayer(
        @Header("Authorization") apiKey:String,
        @Path("id") playerId: Int
    ): String
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.balldontlie.io/"

    val api: MyAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(MyAPIService::class.java)
    }
}