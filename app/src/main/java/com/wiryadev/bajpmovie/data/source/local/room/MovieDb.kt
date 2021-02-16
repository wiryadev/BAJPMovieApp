package com.wiryadev.bajpmovie.data.source.local.room

import android.content.Context
import androidx.room.*
import com.wiryadev.bajpmovie.data.source.local.entity.GenreEntity
import com.wiryadev.bajpmovie.data.source.local.entity.MovieEntity
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity
import com.wiryadev.bajpmovie.utils.DbConverter

@Database(
    entities = [MovieEntity::class, TvEntity::class, GenreEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DbConverter::class)
abstract class MovieDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDb? = null

        fun getInstance(context: Context): MovieDb {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room
                            .databaseBuilder(
                                context.applicationContext,
                                MovieDb::class.java, "movie.db"
                            )
                            .build()
                    }
                }
            }
            return INSTANCE as MovieDb
        }
    }

}