package com.example.marvelapp.domain.usecase

import com.example.marvelapp.domain.mapper.toDomain
import com.example.marvelapp.domain.repository.FavoriteRepository
import com.example.marvelapp.presentation.model.MarvelUiCharacter
import javax.inject.Inject

class RemoveFavoriteCharacterUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(character: MarvelUiCharacter) {
        favoriteRepository.deleteFavoriteCharacter(character.toDomain())
    }
}