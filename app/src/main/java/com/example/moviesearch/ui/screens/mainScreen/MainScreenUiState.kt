package com.example.moviesearch.ui.screens.mainScreen

import com.example.moviesearch.data.model.Movie

data class MainScreenUiState(
    val loading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val movies: List<Movie>? = null,
    val navigateToDetailScreen: Boolean = false
)
