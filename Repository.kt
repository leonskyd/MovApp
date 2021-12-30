package com.example.mymovie.ui.main

interface Repository {
     fun showMovieFromServer(): Movie
    fun showMovieList(): List<Movie>

    fun movieLoaded(movie: Movie?)
    fun addLoadListener(listener: OnLoadListener)
    fun removeLoadListener(listener: OnLoadListener)

    fun interface OnLoadListener{
        fun onLoaded()

    }
}
