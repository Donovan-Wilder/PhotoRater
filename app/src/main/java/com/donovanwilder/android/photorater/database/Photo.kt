package com.donovanwilder.android.photorater.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "photo_rating")
data class Photo(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "photo_url") val photoUrl: String,
    @ColumnInfo(name = "date_added") val dateAdded: Date,
    @ColumnInfo(name = "rating") val rating: Int
)
