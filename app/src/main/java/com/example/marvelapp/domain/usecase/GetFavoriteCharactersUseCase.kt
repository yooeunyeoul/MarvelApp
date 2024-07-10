package com.example.marvelapp.domain.usecase

import com.example.marvelapp.presentation.mapper.toUiModel
import com.example.marvelapp.domain.repository.FavoriteRepository
import com.example.marvelapp.presentation.model.UiFavoriteCharactersState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteCharactersUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(): Flow<UiFavoriteCharactersState> {
        return favoriteRepository.getFavoriteCharacters().map { characters ->
            UiFavoriteCharactersState(
                characters = characters.map { it.toUiModel() },
                loading = false,
                error = null
            )
        }
    }
}