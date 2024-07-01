package com.example.moviesearch.ui.screens.route

sealed class MoviesSearchScreens(val route: String) {
    data object MainScreen: MoviesSearchScreens("MainScreen")
    data object MovieDetailScreen: MoviesSearchScreens("MovieDetailScreen/{id}")

}