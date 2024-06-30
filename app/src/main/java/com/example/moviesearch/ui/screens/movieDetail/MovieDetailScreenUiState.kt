package com.example.moviesearch.ui.screens.movieDetail

import com.example.moviesearch.data.model.MovieDetailResponse
import com.example.moviesearch.utils.connectivity.NetworkStatus

data class MovieDetailScreenUiState(
    val loading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val networkStatus: NetworkStatus = NetworkStatus.Available,
    val movie: MovieDetailResponse? = null
)
