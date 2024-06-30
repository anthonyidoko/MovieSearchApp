package com.example.moviesearch.ui.screens

sealed class MoviesSearchScreens(val route: String) {
    data object MainScreen: MoviesSearchScreens("MainScreen")
    data object MovieDetailScreen: MoviesSearchScreens("MovieDetailScreen/{id}")

}