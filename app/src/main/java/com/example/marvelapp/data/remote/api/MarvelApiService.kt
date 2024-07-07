package com.example.marvelapp.data.remote.api

import com.example.marvelapp.data.remote.dto.MarvelApiResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApiService {
    @GET("characters")
    suspend fun getCharacters(
        @Query("nameStartsWith") nameStartsWith: String,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: String,
        @Query("hash") hash: String
    ): MarvelApiResponseDto
}
