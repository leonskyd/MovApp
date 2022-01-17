package com.example.mymovie.db
import java.util.*
import com.example.mymovie.ui.main.Movie

class LocalRepositoryImpl( private val dao: HistoryDao
        ) : LocalRepository {
    override fun getAllHistory(): List<Movie> {
       return dao.all()
               .map{ entity -> Movie(
               index = entity.index,
               title = entity.title,
               release = entity.release,
               overview = entity.overview,
               rank = entity.rank)
               }
    }

    override fun saveEntity(movie: Movie) {
        dao.insertEntity(
                HistoryEntity(
                        id=0,
                        index=movie.index,
                        title=movie.title,
                        release=movie.release,
                        overview=movie.overview,
                        rank=movie.rank
                )
        )
    }
}
