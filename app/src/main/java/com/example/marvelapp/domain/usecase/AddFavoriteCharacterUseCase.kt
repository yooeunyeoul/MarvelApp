package com.example.marvelapp.domain.usecase

import com.example.marvelapp.domain.model.MarvelCharacter
import com.example.marvelapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddFavoriteCharacterUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(character: MarvelCharacter) {
        favoriteRepository.saveFavoriteCharacter(character)
    }
}