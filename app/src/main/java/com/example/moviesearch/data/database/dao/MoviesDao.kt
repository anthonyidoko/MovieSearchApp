package com.example.moviesearch.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesearch.data.model.Movie
import com.example.moviesearch.data.model.MovieDetailResponse
import kotlinx.coroutines.flow.Flow
@Dao
interface MoviesDao {
    @Query("SELECT * FROM MOVIES_TABLE")
    fun getAllMovies(): Flow<List<MovieDetailResponse>?>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieDetailResponse)

    @Query("SELECT * FROM MOVIES_TABLE WHERE title LIKE '%' || :searchQuery || '%'")
    fun getMovies(searchQuery: String): Flow<List<MovieDetailResponse>>

    @Query("SELECT * FROM MOVIES_TABLE WHERE imdbID =:movieId")
    fun getMovieById(movieId: String): Flow<MovieDetailResponse?>

    @Delete
    suspend fun deleteMovie(movie: MovieDetailResponse)
}