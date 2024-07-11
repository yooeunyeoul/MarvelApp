package com.example.marvelapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_table")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val thumbnailUrl: String,
    val isFavorite: Boolean,
    val addedAt: Long // 추가된 시간 기록을 위한 필드
)