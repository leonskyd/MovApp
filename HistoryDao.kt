package com.example.mymovie.db

import androidx.room.*
@Dao
interface HistoryDao {

        @Query("SELECT * FROM HistoryEntity ORDER BY timestamp DESC" )
        fun all() : List<HistoryEntity>
        @Query("SELECT * FROM HistoryEntity WHERE (city LIKE :word) ORDER BY timestamp DESC" )
        fun getHistoryByMovie(word: String): List<HistoryEntity>
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun insertEntity(entity: HistoryEntity)
        @Update
        fun updateEntity(entity: HistoryEntity)
        @Delete
        fun deleteEntity(entity: HistoryEntity)

}
