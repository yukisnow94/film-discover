package com.filmdiscover.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

val API_KEY = "f1c1fa32aa618e6adc168c3cc3cc6c46"
val LANG = "ru-RU"

interface FilmService {
    @GET("discover/movie")
    suspend fun discoverList(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") lang: String = LANG
    ): TMDbResponse<Film>

    @GET("/search/movie")
    fun searchList(
        @Query("query") searchText: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") lang: String = LANG
    ): List<Film>

    @GET("/movie/{id}")
    fun details(
        @Path("id") id: Int, @Query("api_key") apiKey: String = API_KEY,
        @Query("language") lang: String = LANG
    )

    // Get the cast and crew for a movie.
    @GET("/movie/{id}/credits")
    fun cast(
        @Path("id") id: Int, @Query("api_key") apiKey: String = API_KEY,
        @Query("language") lang: String = LANG
    )

    @GET("/movie/{id}/images")
    fun images(@Path("id") id: Int, @Query("api_key") apiKey: String = API_KEY)

    @GET("/movie/{id}/reviews")
    fun reviews(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") lang: String = LANG
    )

    @GET("/movie/{id}/videos")
    fun videos(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") lang: String = LANG
    )

}

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

var retrofit = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

var service: FilmService = retrofit.create(FilmService::class.java)

