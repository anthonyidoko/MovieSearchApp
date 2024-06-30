package com.example.moviesearch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "movies_table")
data class MovieDetailResponse(
    @SerializedName("Actors")
    val actors: String? = null,
    @SerializedName("Awards")
    val awards: String? = null,
    @SerializedName("BoxOffice")
    val boxOffice: String? = null,
    @SerializedName("Country")
    val country: String? = null,
    @SerializedName("DVD")
    val DVD: String? = null,
    @SerializedName("Director")
    val director: String? = null,
    @SerializedName("Genre")
    val genre: String? = null,
    @SerializedName("Language")
    val language: String? = null,
    @SerializedName("Metascore")
    val metascore: String? = null,
    @SerializedName("Plot")
    val plot: String? = null,
    @SerializedName("Poster")
    val poster: String? = null,
    @SerializedName("Production")
    val production: String? = null,
    @SerializedName("Rated")
    val rated: String? = null,
    @SerializedName("Released")
    val released: String? = null,
    @SerializedName("Response")
    val response: String? = null,
    @SerializedName("Runtime")
    val runtime: String? = null,
    @SerializedName("Title")
    val title: String? = null,
    @SerializedName("Type")
    val type: String? = null,
    @SerializedName("Website")
    val website: String? = null,
    @SerializedName("Writer")
    val writer: String? = null,
    @SerializedName("Year")
    val year: String? = null,
    @SerializedName("imdbID")
    @PrimaryKey
    val imdbID: String = "",
    @SerializedName("imdbRating")
    val imdbRating: String? = null,
    @SerializedName("imdbVotes")
    val imdbVotes: String? = null
)

fun List<MovieDetailResponse>.mapToMovie(): List<Movie>{
    return map { Movie(
        imdbID = it.imdbID,
        title = it.title,
        year = it.year,
        type = it.type,
        poster = it.poster
    ) }

}