package com.example.moviesearch.data.service

import com.example.moviesearch.BuildConfig
import com.example.moviesearch.data.model.MovieDetailResponse
import com.example.moviesearch.data.model.MovieSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieSearchService {
    @GET(".")
    suspend fun searchMovie(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("s") searchQuery: String,
    ): Response<MovieSearchResponse>

    @GET(".")
    suspend fun getMovieById(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("i") id: String,
        @Query("plot") plot: String = "full"
    ): Response<MovieDetailResponse>
}