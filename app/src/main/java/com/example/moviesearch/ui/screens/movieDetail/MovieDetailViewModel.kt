package com.example.moviesearch.ui.screens.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.domain.MainRepository
import com.example.moviesearch.utils.NetworkResource
import com.example.moviesearch.utils.connectivity.NetworkConnectionObserver
import com.example.moviesearch.utils.connectivity.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailScreenUiState())
    val uiState get() = _uiState.asStateFlow()
    private fun getMovieById(movieId: String) {
        viewModelScope.launch {
                when (val response = repository.getMovieById(movieId)) {
                    is NetworkResource.Error -> {
                        _uiState.update {
                            it.copy(
                                loading = false,
                                isError = it.movie != null,
                                errorMessage = response.message ?: "An error has occurred"
                            )
                        }
                    }

                    is NetworkResource.Success -> {
                        _uiState.update {
                            it.copy(
                                loading = false,
                                isError = false,
                                movie = response.data
                            )
                        }
                    }
                }
            }
    }

    fun getMovieFromRoomById(movieId: String) {
        _uiState.update {
            it.copy(
                loading = true
            )
        }
        getMovieById(movieId)
        viewModelScope.launch {
            repository.getMovieFromRoomById(movieId).collectLatest { movie ->
                if (movie != null) {
                    _uiState.update { it.copy(movie = movie, loading = false) }
                }
            }
        }
    }

}