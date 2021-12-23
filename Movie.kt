package com.example.mymovie.ui.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val title: String?,
    val genre: String, // will be changed to Array
    val release: String?,
    val overview: String?,
    val rank: Double?
) : Parcelable


    fun getOneMovie(): Movie =
     Movie("Pulp Fiction", "criminal", "1995", "classic!", 8.9)


    fun getMovieList(): List<Movie> = listOf(
        Movie("Pulp Fiction", "criminal","1995", "classic!", 8.9),
        Movie("Shawshank Redemption", "prison drama", "1992","best ever", 9.2),
        Movie("City of god", "criminal" ,"2008","hard to watch",8.7),
        Movie("Titanic",  "melodrama","1998","you will cry",9.0),
        Movie("Fight Club", "drama","1999","man´s life",8.5))
