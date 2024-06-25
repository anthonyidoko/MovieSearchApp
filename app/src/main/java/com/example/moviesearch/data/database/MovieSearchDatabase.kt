package com.example.moviesearch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesearch.data.database.dao.MoviesDao
import com.example.moviesearch.data.model.Movie

@Database(
    entities = [Movie::class],
    version =  1,
    exportSchema = true
)
abstract class MovieSearchDatabase: RoomDatabase() {
    abstract fun movieSearchDao(): MoviesDao
}