package com.example.marvelapp.data.remote.mapper

import com.example.marvelapp.data.remote.dto.NetworkCharacter
import com.example.marvelapp.domain.model.Character
import com.example.marvelapp.domain.model.Thumbnail

fun NetworkCharacter.toDomain(isFavorite: Boolean) = Character(
    id = id,
    name = name,
    description = description,
    thumbnail = Thumbnail(thumbnail.path, thumbnail.extension),
    isFavorite = isFavorite
)