package com.example.mymovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovie.ui.main.AppState
import com.example.mymovie.ui.main.Repository
import com.example.mymovie.ui.main.RepositoryImpl

class ListViewModel : ViewModel() {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repositorium: Repository = RepositoryImpl()

    fun getData(): LiveData<AppState> = liveDataToObserve


    fun getMovie() {

        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            liveDataToObserve.postValue(AppState.Success(
                repositorium.showMovieList())) }.start()

    }
}