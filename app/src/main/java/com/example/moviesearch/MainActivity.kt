package com.example.moviesearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesearch.ui.screens.route.MoviesSearchScreens
import com.example.moviesearch.ui.screens.mainScreen.MainScreen
import com.example.moviesearch.ui.screens.movieDetail.MovieDetailScreen
import com.example.moviesearch.ui.theme.MovieSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val controller = rememberNavController()
            MovieSearchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = controller,
                        startDestination = MoviesSearchScreens.MainScreen.route
                    ) {
                        composable(
                            route = MoviesSearchScreens.MainScreen.route
                        ) {

                            MainScreen(
                                onNavigate = {
                                    controller.navigate("${MoviesSearchScreens.MovieDetailScreen.route}/$it")
                                })
                        }
                        composable(
                            route = "${MoviesSearchScreens.MovieDetailScreen.route}/{id}",
                            arguments = listOf(navArgument("id")
                            { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("id")
                            MovieDetailScreen(movieId = movieId){
                                controller.popBackStack()
                            }
                        }

                    }


                }
            }
        }
    }
}