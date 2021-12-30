package com.example.mymovie.ui.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val index: Int?,
    val title: String?,
    val release: String?,
    val overview: String?,
    val rank: Double?
) : Parcelable

    fun getOneMovie(): Movie =
     Movie(680,"Pulp Fiction",  "1995", "classic!", 8.9)


fun getMovieList(): List<Movie> = listOf(
        Movie(680,"Pulp Fiction", "1995", "classic!", 8.9),
        Movie(278,"Shawshank Redemption","1995","best ever", 9.2),
        Movie(598,"City of god","2008","hard to watch",8.7),
        Movie(597,"Titanic",  "1998","you will cry",9.0),
        Movie(550,"Fight Club", "1999","man´s life",8.5))
