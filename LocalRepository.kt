package com.example.mymovie.db

import com.example.mymovie.ui.main.Movie

interface LocalRepository {
    fun getAllHistory(): List<Movie>
    fun saveEntity(movie: Movie)
}