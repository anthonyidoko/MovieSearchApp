package com.example.moviesearch.di.appModule

import android.content.Context
import androidx.room.Room
import com.example.moviesearch.BuildConfig
import com.example.moviesearch.data.database.MovieSearchDatabase
import com.example.moviesearch.data.service.MovieSearchService
import com.example.moviesearch.utils.DispatchProvider
import com.example.moviesearch.utils.MovieSearchDispatcher
import com.example.moviesearch.utils.connectivity.NetworkConnectionObserver
import com.example.moviesearch.utils.connectivity.NetworkConnectionObserverImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
            name = MovieSearchDatabase.NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val builder = OkHttpClient.Builder()
        builder.connectTimeout(70, TimeUnit.SECONDS)
            .writeTimeout(70, TimeUnit.SECONDS)
            .readTimeout(70, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieSearchService(retrofit: Retrofit): MovieSearchService {
        return retrofit.create(MovieSearchService::class.java)
    }

    @Singleton
    @Provides
    fun provideDispatcher(): DispatchProvider = MovieSearchDispatcher()

    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): NetworkConnectionObserver {
        return NetworkConnectionObserverImpl(context)
    }

}