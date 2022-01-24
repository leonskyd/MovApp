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
    
    private val movieApi = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()))
        .build()
        .create(MovieApi::class.java)

    fun loadWithRetro(id: Int?, listener: OnMovieLoadListener) {
        if (id != null) {
            movieApi.getMovie(id, API_KEY)
                .enqueue(object: retrofit2.Callback<WebMovie>{
                    override fun onResponse(
                        call: retrofit2.Call<WebMovie>,
                        response: retrofit2.Response<WebMovie>
                    ) {
                        if(response.isSuccessful) {
                            response.body()?.let{
                                listener.onLoaded(it)                     }
                        } else {
                            listener.onFailed(Exception(response.message()))
                            Log.e("DEBUGLOG", "FATAL CONNECTION $response")
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<WebMovie>, t: Throwable) {
                        listener.onFailed(t)
                    }

                })
        }
    }

    fun loadMovieFromWeb(id: Int?, listener: OnMovieLoadListener) {


        lateinit var urlConnection: HttpsURLConnection

            try {
                val uri = URL("https://api.themoviedb.org/3/movie/${id}?api_key=${API_KEY}")
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                urlConnection.addRequestProperty("API Key (v3 auth)", API_KEY)

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
