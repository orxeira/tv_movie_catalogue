package com.orxeira.tvapp.data.database

import androidx.room.*
import com.orxeira.tvapp.data.database.model.TvShow

@Dao
interface TvShowDao {

    @Query("SELECT * FROM TvShow")
    fun getAll(): List<TvShow>

    @Query("SELECT * FROM TvShow WHERE id = :id")
    fun findById(id: Int): TvShow

    @Query("SELECT COUNT(id) FROM TvShow")
    fun tvShowCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTvShows(tvShows: List<TvShow>)

    @Update
    fun updateTvShow(tvShow: TvShow)
}