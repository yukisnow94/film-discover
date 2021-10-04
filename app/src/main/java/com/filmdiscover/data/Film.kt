package com.filmdiscover.data

import com.squareup.moshi.Json

data class Film(
    val id: Int? = null,
    val title: String,
    @Json(name = "original_title") val originalTitle: String? = null,
    @Json(name = "vote_average") val rating: Double,
    @Json(name = "poster_path") val posterPath: String? = null
) {
    val imageUrl = "https://image.tmdb.org/t/p/w500${posterPath}"
}

data class TMDbResponse<T>(
    val page: Int,
    val results: List<T>,
    val total_results: Int,
    val total_pages: Int
)