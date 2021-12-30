package com.example.mymovie.ui.main

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log

class IntentService : IntentService("IntentService") {

    companion object { const val TAG = "IntentService"
        const val MOVIE_INFO = "MOVIE_INFO"
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "current thread : " + Thread.currentThread().name)
        Thread.sleep(3000)

        intent?.getParcelableExtra<Movie>(MOVIE_INFO)?.let{movie ->
            MovieLoader.loadMovieFromWeb(550, object:MovieLoader.OnMovieLoadListener {

                override fun onLoaded(webMovie: WebMovie) {
                    applicationContext.sendBroadcast(Intent(
                            applicationContext, Receiver::class.java).apply {
                        action = Receiver.MOVIE_LOAD_SUCCESS
                        putExtra(MOVIE_INFO, Movie(
                                //temperature = weatherDTO.fact?.temp ?: 0,
                                index = webMovie.id,
                                title = webMovie.original_title,
                                release = webMovie.release_date,
                                overview = webMovie.overview,
                                rank = webMovie.vote_average
                        ))                     } )
                }


                override fun onFailed(throwable: Throwable) {
                    applicationContext.sendBroadcast(Intent(
                            applicationContext, Receiver::class.java).apply {
                        action = Receiver.MOVIE_LOAD_FAILED
                    } )
                }

            })
        }

    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "on Create")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
        Log.d(TAG, "on Start")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "on Destroy")
    }
}