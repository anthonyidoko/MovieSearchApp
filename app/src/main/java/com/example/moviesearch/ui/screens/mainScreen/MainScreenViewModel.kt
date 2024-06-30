package com.example.moviesearch.ui.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.data.model.Movie
import com.example.moviesearch.data.model.MovieDetailResponse
import com.example.moviesearch.data.model.mapToMovie
import com.example.moviesearch.domain.MainRepository
import com.example.moviesearch.utils.NetworkResource
import com.example.moviesearch.utils.connectivity.NetworkConnectionObserver
import com.example.moviesearch.utils.connectivity.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: MainRepository,
    private val networkObserver: NetworkConnectionObserver,
) : ViewModel() {

    private var job: Job? = null

    private val _mainScreenUiState = MutableStateFlow(MainScreenUiState())
    val mainScreenUiState get() = _mainScreenUiState.asStateFlow()

    private val databaseMovies = MutableStateFlow<List<Movie>>(emptyList())


    private fun searchMovies(searchQuery: String) {
        job?.cancel()
        _mainScreenUiState.update {
            it.copy(
                loading = true
            )
        }
        job = viewModelScope.launch {
            delay(2000)
            observeNetwork {
                when (val response = repository.searchMovie(searchQuery)) {
                    is NetworkResource.Error -> {
                        _mainScreenUiState.update {
                            it.copy(
                                loading = false,
                                isError = true,
                                errorMessage = response.message,
                            )
                        }
                    }

                    is NetworkResource.Success -> {
                        _mainScreenUiState.update {
                            it.copy(loading = false, isError = false, movies = response.data)
                        }
                    }
                }
            }
        }
    }

    fun getMoviesFromRoomDatabase() {
        _mainScreenUiState.update {
            it.copy(
                loading = true
            )
        }
        viewModelScope.launch {
            repository.getMovies().collectLatest { movies ->
                movies?.let { items ->
                    if (items.isNotEmpty()) {
                        databaseMovies.update { movies.reversed().mapToMovie() }
                        _mainScreenUiState.update {
                            it.copy(
                                loading = false,
                                movies = movies.mapToMovie()
                            )
                        }
                    }
                }

                _mainScreenUiState.update {
                    it.copy(
                        loading = false
                    )
                }
            }

        }
    }

    fun performSearch(searchQuery: String) {
        val trimmedQuery = searchQuery.trim()
        val movies = databaseMovies.value
        if (trimmedQuery.isEmpty() && movies.isNotEmpty()) {
            _mainScreenUiState.update {
                it.copy(
                    movies = movies,
                    isError = false,
                    errorMessage = ""
                )
            }
            return
        }
        val filteredMovies = movies.filter {
            it.title?.contains(searchQuery, true) == true
        }
        if (filteredMovies.isNotEmpty()) {
            _mainScreenUiState.update {
                it.copy(
                    movies = filteredMovies
                )
            }
        }

        if (trimmedQuery.length < 3) {
            return
        }

        searchMovies(trimmedQuery)
    }

    fun resetMainScreenUiState() {
        _mainScreenUiState.update {
            MainScreenUiState()
        }
    }

    private fun observeNetwork(block: suspend () -> Unit) {
        viewModelScope.launch {
            networkObserver.observe().collectLatest { status ->
                when (status) {
                    NetworkStatus.Available -> {
                        block()
                    }

                    NetworkStatus.UnAvailable -> {
                        _mainScreenUiState.update {
                            it.copy(errorMessage = "Turn on your internet", loading = false)
                        }
                    }

                    NetworkStatus.Lost -> {
                        _mainScreenUiState.update {
                            it.copy(errorMessage = "Internet connection lost", loading = false)
                        }
                    }

                }
            }
        }
    }
}

