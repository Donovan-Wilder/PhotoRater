package com.donovanwilder.android.photorater.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Photo::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class PhotoRaterDatabase: RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}