package com.example.moviesearch.di.appModule

import android.content.Context
import androidx.room.Room
import com.example.moviesearch.data.database.MovieSearchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideMovieSearchDatabase(
        @ApplicationContext context: Context
    ): MovieSearchDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MovieSearchDatabase::class.java,
            name = "movie_search_database",
        ).build()
    }

}