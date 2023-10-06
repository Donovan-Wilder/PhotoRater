package com.donovanwilder.android.photorater.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface PhotoDao {
    @Query("SELECT * FROM photo_rating")
    fun getAll(): List<Photo>

    @Query("SELECT * FROM photo_rating WHERE photo_url = :url ")
    fun getPhoto(url: String): List<Photo>
    @Insert
    fun add(record: Photo)

}