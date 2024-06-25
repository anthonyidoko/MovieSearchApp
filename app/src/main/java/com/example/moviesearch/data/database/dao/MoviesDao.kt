package com.example.moviesearch.data.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesearch.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesDao {
    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<List<Movie>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: Movie)

    @Query("SELECT * FROM movie WHERE title LIKE '%' || :searchQuery || '%'")
    fun getMovies(searchQuery: String): Flow<List<Movie>>

    @Delete
    suspend fun deleteMovie(movie: Movie)
}