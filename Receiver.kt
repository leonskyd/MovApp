package com.example.mymovie.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class Receiver : BroadcastReceiver() {

    private val repositorium: Repository = RepositoryImpl()

    companion object {
        const val MOVIE_LOAD_SUCCESS = "MOVIE_LOAD_SUCCESS"
        const val MOVIE_LOAD_FAILED = "MOVIE_LOAD_FAILED"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("MaiReceiver", "onReceive $intent")

        when(intent.action) {
            MOVIE_LOAD_SUCCESS -> repositorium.movieLoaded(
                intent.extras?.getParcelable("MOVIE_INFO")
            )
            MOVIE_LOAD_FAILED -> repositorium.movieLoaded(null)
        }
    }
}