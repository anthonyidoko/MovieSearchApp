package com.example.moviesearch.domain

import com.example.moviesearch.data.model.Movie
import com.example.moviesearch.data.model.MovieDetailResponse
import com.example.moviesearch.utils.NetworkResource
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun searchMovie(searchQuery: String): NetworkResource<List<Movie>>
    suspend fun getMovieById(id: String): NetworkResource<MovieDetailResponse>
    fun getMovieFromRoomById(movieId: String): Flow<MovieDetailResponse?>
    fun getMovies(): Flow<List<MovieDetailResponse>?>
    fun <T> processError(exception: Exception): NetworkResource<T>


}