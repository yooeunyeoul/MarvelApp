package com.example.marvelapp.domain.usecase

import com.example.marvelapp.domain.model.MarvelCharacter
import com.example.marvelapp.domain.repository.CharacterRepository
import com.example.marvelapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(nameStartsWith: String, offset: Int, limit: Int): List<MarvelCharacter> {
        val characters = characterRepository.getCharacters(nameStartsWith, offset, limit)
        val favoriteIds = favoriteRepository.getFavoriteCharacterIds()
        return characters.map { character ->
            character.copy(isFavorite = favoriteIds.contains(character.id))
        }
    }
}