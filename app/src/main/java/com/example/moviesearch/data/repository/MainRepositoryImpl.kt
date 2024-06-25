package com.example.moviesearch.data.repository

import com.example.moviesearch.data.database.MovieSearchDatabase
import com.example.moviesearch.domain.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    database: MovieSearchDatabase
): MainRepository {
    private val dao = database.movieSearchDao()
}