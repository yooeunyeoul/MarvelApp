package com.example.marvelapp.data.repositoryimpl

import com.example.marvelapp.data.local.dao.FavoriteDao
import com.example.marvelapp.data.local.mapper.toDomain
import com.example.marvelapp.data.local.mapper.toEntity
import com.example.marvelapp.domain.model.MarvelCharacter
import com.example.marvelapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {

    override suspend fun getFavoriteCharacterIds(): List<Int> {
        return favoriteDao.getAllFavorites().map { it.id }
    }

    override fun getFavoriteCharacters(): Flow<List<MarvelCharacter>> {
        return favoriteDao.getAllFavoritesFlow().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveFavoriteCharacter(character: MarvelCharacter) {
        favoriteDao.replaceOldestIfNeeded(character.toEntity())
    }

    override suspend fun deleteFavoriteCharacter(character: MarvelCharacter) {
        favoriteDao.delete(character.toEntity())
    }
}