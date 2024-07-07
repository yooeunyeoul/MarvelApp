package com.example.marvelapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marvelapp.data.local.entity.CharacterEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM character_table")
    suspend fun getAllFavorites(): List<CharacterEntity>

    @Insert
    suspend fun insert(character: CharacterEntity)

    @Delete
    suspend fun delete(character: CharacterEntity)
}