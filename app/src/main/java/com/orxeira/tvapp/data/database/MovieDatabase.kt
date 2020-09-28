package com.orxeira.tvapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.orxeira.tvapp.data.database.model.Movie
import com.orxeira.tvapp.data.database.model.TvShow

@Database(entities = [Movie::class, TvShow::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie-db"
        ).build()
    }

    abstract fun movieDao(): MovieDao

    abstract fun tvShowDao(): TvShowDao
}