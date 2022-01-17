package com.example.mymovie.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class HistoryEntity (
        @PrimaryKey
        val id: Long,
        val index: Int?,
        val title: String?,
        val release: String?,
        val overview: String?,
        val rank: Double?
        )
