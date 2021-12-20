package com.example.mymovie.ui.main

class RepositoryImpl : Repository{
    override fun showMovieFromServer(id: Int): Movie {
        return getOneMovie()
    }

    override fun showMovieList(): List<Movie> {
        return getMovieList()
    }
}