package com.example.mymovie.db

import android.app.Application
import androidx.room.Room

class AppForDataBase: Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
    companion object {
        private var appInstance: AppForDataBase? = null
        private var db: HistoryDataBase? = null
        private const val DB_NAME = "History.db"

        fun getHistoryDao(): HistoryDao {

            if (db == null) {
                synchronized(HistoryDataBase::class.java) {
                    if (db == null) {
                        appInstance?.let{app ->
                            db = Room.databaseBuilder(
                                    app.applicationContext,
                                    HistoryDataBase::class.java,
                                    DB_NAME
                            ).allowMainThreadQueries()
                                    .build()
                        } ?: throw Exception("Can it be ?")
                    }
                }
            }

            return db!!.historyDao()
        }
    }
}