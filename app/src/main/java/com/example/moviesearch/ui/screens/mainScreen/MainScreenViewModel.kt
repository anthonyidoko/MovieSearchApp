package com.example.moviesearch.ui.screens.mainScreen

import androidx.lifecycle.ViewModel
import com.example.moviesearch.domain.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel()