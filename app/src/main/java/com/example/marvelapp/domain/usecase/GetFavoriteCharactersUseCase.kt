package com.example.marvelapp.domain.usecase

import com.example.marvelapp.domain.model.MarvelCharacter
import com.example.marvelapp.domain.repository.FavoriteRepository
import com.example.marvelapp.domain.util.ResultState
import com.example.marvelapp.domain.util.asResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteCharactersUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(): Flow<ResultState<List<MarvelCharacter>>> {
        return favoriteRepository.getFavoriteCharacters().asResult()
    }
}