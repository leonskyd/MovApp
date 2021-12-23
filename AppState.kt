package com.example.mymovie.ui.main

sealed class AppState {
    data class Success(val movieData: List<Movie>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()

}
