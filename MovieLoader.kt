package com.example.mymovie.ui.main

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import com.google.gson.Gson
import java.util.stream.Collectors

object MovieLoader { //https://api.themoviedb.org/3/movie/550?api_key=01141597fb9a845a9ce999e83b8db575

    private const val API_KEY: String = "01141597fb9a845a9ce999e83b8db575"

    fun loadMovieFromWeb(id: Int?, listener: OnMovieLoadListener) {


        lateinit var urlConnection: HttpsURLConnection

            try {
                val uri = URL("https://api.themoviedb.org/3/movie/${id}?api_key=${API_KEY}")
                    urlConnection = uri.openConnection() as HttpsURLConnection

                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 1000
                    urlConnection.connectTimeout = 1000
                Log.d("TAG", urlConnection.toString())
                    val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                Log.d("TAG", "before lines 2")

                //getLinesForOld(bufferedReader)
                val webMovie = Gson().fromJson(getLines(reader), WebMovie::class.java)
                Log.d("TAG", webMovie?.overview.toString()) // webMovie получен по id
                listener.onLoaded(webMovie)
                } catch (e: Exception) {
                Log.d("TAG", "exception")
                    listener.onFailed(e)
                } finally {
                    urlConnection?.disconnect() // dont forget
                }
    }

    interface OnMovieLoadListener {
        fun onLoaded(webMovie: WebMovie)
        fun onFailed(throwable: Throwable)
    }

    private fun getLinesForOld(reader: BufferedReader): String {
        val rawData = StringBuilder(1024)
        var temVariable: String?
        while (reader.readLine().also { temVariable = it } != null) {
            rawData.append(temVariable).append("\n")
        }
        reader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    // requires API24, we have 21
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}