package com.example.marvelapp.domain.usecase

import com.example.marvelapp.domain.mapper.toUiModel
import com.example.marvelapp.domain.repository.FavoriteRepository
import com.example.marvelapp.presentation.model.MarvelUiCharacter
import javax.inject.Inject

class GetFavoriteCharactersUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(): List<MarvelUiCharacter> {
        return favoriteRepository.getFavoriteCharacters().map { it.toUiModel() }
    }
}