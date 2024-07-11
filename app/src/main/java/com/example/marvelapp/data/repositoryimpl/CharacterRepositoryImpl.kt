package com.example.marvelapp.data.repositoryimpl

import com.example.marvelapp.data.remote.api.MarvelApiService
import com.example.marvelapp.data.remote.mapper.toDomain
import com.example.marvelapp.data.util.HashUtil
import com.example.marvelapp.domain.model.MarvelCharacterList
import com.example.marvelapp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: MarvelApiService,
    private val apiKey: String,
    private val privateKey: String
) : CharacterRepository {

    override fun getCharacters(
        nameStartsWith: String,
        offset: Int,
        limit: Int
    ): Flow<MarvelCharacterList> = flow {
        val timestamp = System.currentTimeMillis().toString()
        val hash = HashUtil.md5("$timestamp$privateKey$apiKey")
        val response =
            apiService.getCharacters(nameStartsWith, limit, offset, apiKey, timestamp, hash)
        emit(response.toDomain())
    }
}