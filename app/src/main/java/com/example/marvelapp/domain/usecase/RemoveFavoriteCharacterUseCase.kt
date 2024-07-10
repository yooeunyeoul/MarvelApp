package com.example.marvelapp.domain.usecase

import com.example.marvelapp.domain.model.MarvelCharacter
import com.example.marvelapp.presentation.mapper.toDomain
import com.example.marvelapp.domain.repository.FavoriteRepository
import com.example.marvelapp.presentation.model.UiMarvelCharacter
import javax.inject.Inject

class RemoveFavoriteCharacterUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(character: MarvelCharacter) {
        favoriteRepository.deleteFavoriteCharacter(character)
    }
}