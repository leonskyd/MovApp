package com.example.mymovie.ui.main

interface Repository {
    fun showMovieFromServer(id: Int): Movie
    fun showMovieList(): List<Movie>
}