package com.example.moviesearch.ui.screens.mainScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.moviesearch.R
import com.example.moviesearch.data.model.Movie
import com.example.moviesearch.ui.screens.commons.ErrorScreen
import com.example.moviesearch.ui.screens.commons.LoadingDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getMoviesFromRoomDatabase()
        viewModel.performSearch("")
    }
    val uiState by viewModel.mainScreenUiState.collectAsState()
    MainScreen(
        uiState = uiState,
        onSearchClick = viewModel::performSearch,
        onMovieClick = {
            viewModel.resetMainScreenUiState()
            onNavigate.invoke(it.imdbID ?: "")
        },

        )
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    uiState: MainScreenUiState,
    onSearchClick: (String) -> Unit,
    onMovieClick: (Movie) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(horizontal = 20.dp)
    ) {
        var searchInput by rememberSaveable {
            mutableStateOf("")
        }
        SearchBar(modifier = Modifier.fillMaxWidth(),
            input = searchInput,
            onInputChange = {
                searchInput = it
                onSearchClick.invoke(searchInput)
            }
        )

        if (uiState.loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingDialog()
            }
        }
        if (uiState.isError) {
            ErrorScreen(
                title = "Error",
                message = uiState.errorMessage ?: "An error has occurred",
                onDismiss = {
                    searchInput = ""
                    onSearchClick.invoke(searchInput)
                },
                onAction = {
                    searchInput = ""
                    onSearchClick.invoke(searchInput)
                }

            )
            return
        }

        uiState.movies?.let { movies ->
            MoviesLazyColum(
                modifier = Modifier.padding(top = 10.dp),
                movies = movies,
                onMovieClick = onMovieClick
            )
        }

    }
}

@Composable
fun MoviesLazyColum(
    modifier: Modifier = Modifier,
    movies: List<Movie>, onMovieClick: (Movie) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(movies) { movie ->
            MovieCardItem(
                movie = movie, onMovieClick = onMovieClick
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieCardItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClick: (Movie) -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onMovieClick(movie) }
            .fillMaxSize(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        )

    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            model = movie.poster ?: "",
            contentDescription = stringResource(id = R.string.movie_poster),
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .weight(1f)
                .padding(start = 2.dp)) {
                Text(
                    text = movie.title ?: "",
                    modifier = Modifier.basicMarquee(
                        animationMode = MarqueeAnimationMode.Immediately,
                        delayMillis = 0,
                    ),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily(Font(R.font.playwrite_wght)),
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                Text(text = "")
            }

            Text(
                text = movie.year ?: "",
                modifier = Modifier
                    .basicMarquee(
                        animationMode = MarqueeAnimationMode.Immediately,
                        delayMillis = 0,
                    )
                    .padding(end = 2.dp),
                style = MaterialTheme.typography.bodySmall
                .copy(
                    fontWeight = FontWeight.ExtraLight,
                    fontFamily = FontFamily(Font(R.font.playwrite_thin)),
                    color = MaterialTheme.colorScheme.onTertiary
                )
            )

        }

    }
}


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    input: String = "",
    onInputChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = input, onValueChange = onInputChange,
        textStyle = placeHolderTextStyle().copy(
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = MaterialTheme.colorScheme.tertiary,
            letterSpacing = 2.sp
        ),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        shape = RoundedCornerShape(35.dp),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.secondary,
            focusedBorderColor = MaterialTheme.colorScheme.tertiary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.enter_movie_title_to_search),
                style = placeHolderTextStyle()
            )
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
    )

}


@Composable
fun placeHolderTextStyle(): TextStyle {
    return MaterialTheme.typography.labelLarge.copy(
        fontWeight = FontWeight.Light,
        color = MaterialTheme.colorScheme.onSecondary,
        fontFamily = FontFamily(Font(R.font.playwrite_regular))
    )
}

