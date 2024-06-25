package com.example.moviesearch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String? = null
)
