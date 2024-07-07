package com.example.marvelapp.data.remote.dto

data class MarvelCharacterDto(
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: ThumbnailDto,
    val resourceURI: String,
    val comics: ComicsDto,
    val series: SeriesDto,
    val stories: StoriesDto,
    val events: EventsDto,
    val urls: List<UrlDto>
)