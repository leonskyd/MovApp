package com.example.mymovie.ui.main

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovie.db.AppForDataBase
import com.example.mymovie.db.LocalRepository
import com.example.mymovie.db.LocalRepositoryImpl


class MainViewModel (
    private val repository: Repository
) : ViewModel(), LifecycleObserver{

    val liveDataToRead: MutableLiveData<AppState> = MutableLiveData()
    private val localRepo: LocalRepository = LocalRepositoryImpl(AppForDataBase.getHistoryDao())

    fun saveHistory(movie: Movie) {
        localRepo.saveEntity(movie)
    }


    /*fun LoadMovieFromWeb (id: Int?) {
        liveDataToRead.value = AppState.Loading
        Thread{
            val data = repository.showMovieFromServer(id)
            liveDataToRead.postValue(AppState.Success(listOf(data)))
        }.start()

    }*/
}
