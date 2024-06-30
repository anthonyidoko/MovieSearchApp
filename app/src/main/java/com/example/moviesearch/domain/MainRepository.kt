package com.example.moviesearch.domain

import com.example.moviesearch.data.model.Movie
import com.example.moviesearch.data.model.MovieDetailResponse
import com.example.moviesearch.utils.NetworkConnectivityException
import com.example.moviesearch.utils.NetworkResource
import com.example.moviesearch.utils.connectivity.NetworkConnectionObserver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

interface MainRepository {
    suspend fun searchMovie(searchQuery: String): NetworkResource<List<Movie>>
    suspend fun getMovieById(id: String): NetworkResource<MovieDetailResponse>
    fun getMovieFromRoomById(movieId: String): Flow<MovieDetailResponse?>
    fun getMovies(): Flow<List<MovieDetailResponse>?>


}