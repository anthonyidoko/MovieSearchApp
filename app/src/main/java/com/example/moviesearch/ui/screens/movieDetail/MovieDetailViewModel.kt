package com.example.moviesearch.ui.screens.movieDetail

import androidx.lifecycle.ViewModel
import com.example.moviesearch.domain.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel()