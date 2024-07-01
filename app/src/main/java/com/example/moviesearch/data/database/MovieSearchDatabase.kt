package com.example.moviesearch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesearch.data.database.dao.MoviesDao
import com.example.moviesearch.data.model.Movie
import com.example.moviesearch.data.model.MovieDetailResponse

@Database(
    entities = [MovieDetailResponse::class],
    version =  1,
    exportSchema = true
)
abstract class MovieSearchDatabase: RoomDatabase() {
    abstract fun movieSearchDao(): MoviesDao

    companion object{
        const val NAME = "movie_search_database"
    }
}