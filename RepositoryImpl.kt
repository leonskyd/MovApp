package com.example.mymovie.ui.main

class RepositoryImpl : Repository{
   private val listeners: MutableList<Repository.OnLoadListener> = mutableListOf()
    private var movie: Movie? = null

    override fun showMovieFromServer(): Movie {
        return getOneMovie()
    }

    /* override fun showMovieFromServer(id: Int?): Movie {
         val info = MovieLoader.loadMovieFromWeb(id)
         return Movie(
                 index = info?.id,
             title = info?.original_title,
             release = info?.release_date,
             overview = info?.overview,
             rank = info?.vote_average
         )
     }*/
    override fun showMovieList(): List<Movie> = getMovieList()

    override fun movieLoaded(movie: Movie?) {
        this.movie = movie
        listeners.forEach{it.onLoaded()}
    }

    override fun addLoadListener(listener: Repository.OnLoadListener) {
        listeners.add(listener)
    }

    override fun removeLoadListener(listener: Repository.OnLoadListener) {
        listeners.remove(listeners)
    }
}
