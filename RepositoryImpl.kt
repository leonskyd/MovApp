package com.example.mymovie.ui.main

class RepositoryImpl : Repository{
  override fun showMovieFromServer(id: Int): Movie = getOneMovie()
    override fun showMovieList(): List<Movie> = getMovieList()
}
