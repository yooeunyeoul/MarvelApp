package com.example.marvelapp.domain.usecase

import com.example.marvelapp.domain.mapper.toUiModel
import com.example.marvelapp.domain.repository.FavoriteRepository
import com.example.marvelapp.presentation.model.UiMarvelCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteCharactersUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(): Flow<List<UiMarvelCharacter>> {
        return favoriteRepository.getFavoriteCharacters().map { characters ->
            characters.map { it.toUiModel() }
        }
    }
}