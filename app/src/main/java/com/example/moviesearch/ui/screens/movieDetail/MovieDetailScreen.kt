package com.example.moviesearch.ui.screens.movieDetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.example.moviesearch.R
import com.example.moviesearch.data.model.MovieDetailResponse
import com.example.moviesearch.ui.screens.MoviesSearchScreens
import com.example.moviesearch.ui.screens.commons.ErrorScreen
import com.example.moviesearch.ui.screens.commons.LoadingDialog
import com.example.moviesearch.ui.screens.mainScreen.MainScreenViewModel
import com.example.moviesearch.utils.connectivity.NetworkStatus

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    movieId: String?,
    onNavigate: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = Unit) {
        movieId?.let {
            viewModel.getMovieFromRoomById(it)
        }
    }

    MovieDetailScreen(
        uiState = uiState, onNavigate = onNavigate,
        onRetry = {
            movieId?.let {
                viewModel.getMovieFromRoomById(it)
            }
        }
    )
}

@Composable
fun MovieDetailScreen(
    uiState: MovieDetailScreenUiState,
    onNavigate: () -> Unit,
    onRetry: () -> Unit,

    ) {

    if (uiState.loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingDialog(
                message = "Please wait"
            )
        }
    }

    if (uiState.isError || (uiState.movie == null && uiState.networkStatus != NetworkStatus.Available)) {
        ErrorScreen(
            title = "Error",
            message = uiState.errorMessage,
            buttonText = "Retry",
            onAction = onRetry,
            onDismiss = onNavigate
        )
        return
    }


    uiState.movie?.let {
        MovieDetailScreen(movie = it)
    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    movie: MovieDetailResponse
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Card(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),

            ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = movie.poster ?: "",
                contentDescription = "movie poster",
                contentScale = ContentScale.FillBounds
            )
        }

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {

            Text(
                text = movie.title ?: " Details",
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(
                        animationMode = MarqueeAnimationMode.Immediately,
                        delayMillis = 0,
                    )
                    .padding(horizontal = 20.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily(Font(R.font.playwrite_wght)),
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            movie.genre?.let { TextItem(title = "Genre", body = it) }
            movie.actors?.let { TextItem(title = "Actors", body = it) }
            movie.imdbRating?.let { TextItem(title = "Movie Rating", body = "$it*") }
            movie.awards?.let { TextItem(title = "Awards", body = it) }
            movie.plot?.let { TextItem(title = "Plot", body = it) }
            movie.released?.let { TextItem(title = "Released", body = it) }
            movie.director?.let { TextItem(title = "Director", body = it) }
            movie.runtime?.let { TextItem(title = "Runtime", body = it) }
            movie.country?.let { TextItem(title = "Country", body = it) }
            movie.rated?.let { TextItem(title = "Rated", body = it) }
            movie.writer?.let { TextItem(title = "Writer", body = it) }

        }
    }
}

@Composable
fun TextItem(
    modifier: Modifier = Modifier,
    title: String,
    body: String
) {
    Column(modifier = modifier.padding(vertical = 10.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(
                fontFamily = FontFamily(Font(R.font.playwrite_wght)),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.ExtraBold
            )
        )

        Text(
            text = body,
            modifier = Modifier.padding(horizontal = 10.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                color = MaterialTheme.colorScheme.onSurface
            )
        )

    }

}