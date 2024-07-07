package com.example.marvelapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marvelapp.data.local.dao.FavoriteDao
import com.example.marvelapp.data.local.entity.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}