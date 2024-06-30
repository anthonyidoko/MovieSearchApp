package com.example.moviesearch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @SerializedName("Title")
    val title: String? = null,
    @SerializedName("Year")
    val year: String? = null,
    @SerializedName("Poster")
    val poster: String? = null,
    @SerializedName("Type")
    val type: String? = null,
    @SerializedName("imdbID")
    val imdbID: String? = null
)

//{"Ratings":[{"Source":"Internet Movie Database","Value":"8.1/10"}],"Metascore":"N/A","imdbRating":"8.1","imdbVotes":"52,713","imdbID":"tt3281796","Type":"series","totalSeasons":"6","Response":"True"}

