package com.example.moviesearch.data.repository

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.data.database.MovieSearchDatabase
import com.example.moviesearch.data.model.Movie
import com.example.moviesearch.data.model.MovieDetailResponse
import com.example.moviesearch.data.model.MovieSearchResponse
import com.example.moviesearch.data.service.MovieSearchService
import com.example.moviesearch.domain.MainRepository
import com.example.moviesearch.utils.DispatchProvider
import com.example.moviesearch.utils.NetworkConnectivityException
import com.example.moviesearch.utils.NetworkResource
import com.example.moviesearch.utils.connectivity.NetworkConnectionObserver
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val service: MovieSearchService,
    private val dispatcher: DispatchProvider,
    database: MovieSearchDatabase
) : MainRepository {
    private val movieDao = database.movieSearchDao()
    override suspend fun searchMovie(searchQuery: String): NetworkResource<List<Movie>> {
        return try {
            withContext(dispatcher.io()) {
                val response = service.searchMovie(searchQuery = searchQuery)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.response?.contains("True", true) == false) {
                        return@withContext NetworkResource.Error(body.error)
                    }
                    return@withContext NetworkResource.Success(body?.search)
                }
                val errorResponse =
                    Gson().fromJson(response.errorBody()?.string(), MovieSearchResponse::class.java)
                return@withContext NetworkResource.Error(errorResponse.error)
            }

        } catch (e: Exception) {
            NetworkResource.Error("An Error has occurred. Please try again")
        }
    }

    override suspend fun getMovieById(id: String): NetworkResource<MovieDetailResponse> {
        return try {
            withContext(dispatcher.io()) {
                val response = service.getMovieById(id = id)
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let { movie ->
                        movieDao.saveMovie(movie)
                    }
                    return@withContext NetworkResource.Success(body)
                }

                return@withContext NetworkResource.Error("")
            }

        } catch (e: Exception) {
            NetworkResource.Error("An error has occurred")
        }
    }

    override fun getMovieFromRoomById(movieId: String): Flow<MovieDetailResponse?> {
        return movieDao.getMovieById(movieId)
    }

    override fun getMovies(): Flow<List<MovieDetailResponse>?> {
        return movieDao.getAllMovies()
    }


}