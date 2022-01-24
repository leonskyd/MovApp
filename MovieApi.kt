package com.example.mymovie.ui.main

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET(value = "3/movie/{movie_id}")
    fun getMovie (
        @Path("movie_id") id: Int,
        @Query("api_key") token: String
            ): Call<WebMovie>
}