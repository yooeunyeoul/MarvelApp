package com.example.marvelapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.marvelapp.data.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM character_table ORDER BY addedAt ASC")
    fun getAllFavoritesFlow(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM character_table ORDER BY addedAt ASC")
    suspend fun getAllFavorites(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterEntity)

    @Delete
    suspend fun delete(character: CharacterEntity)

    @Transaction
    suspend fun replaceOldestIfNeeded(character: CharacterEntity) {
        val favoriteCharacters = getAllFavorites()
        if (favoriteCharacters.size >= 5) {
            // 가장 오래된 아이템 삭제
            val oldestCharacter = favoriteCharacters.first()
            delete(oldestCharacter)
        }
        insert(character)
    }
}