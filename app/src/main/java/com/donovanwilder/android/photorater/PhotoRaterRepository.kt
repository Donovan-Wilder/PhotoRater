package com.donovanwilder.android.photorater

import android.content.Context
import androidx.room.Room
import com.donovanwilder.android.photorater.data.FlickerApi
import com.donovanwilder.android.photorater.database.Photo
import com.donovanwilder.android.photorater.database.PhotoRaterDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhotoRaterRepository private constructor(val context: Context) {
    private val flickerApi = FlickerApi
    private val photoBuffer = ArrayDeque<String>()
    private val db by lazy {
        Room.databaseBuilder(
            context,
            PhotoRaterDatabase::class.java,
            "photorater-db"
        ).build()
    }

    private suspend fun getPhotoBuffer(): ArrayDeque<String> {
        topOffPhotos()
        return photoBuffer
    }

    private suspend fun topOffPhotos() {
        if (photoBuffer.size > 5) {
            return
        }
        val uncheckedList = flickerApi.getPhotosUrl()
        val checkedList = arrayListOf<String>()
        uncheckedList.forEach {
            if (!existInDatabase(it)) {
                checkedList.add(it)
            }
        }
        photoBuffer.addAll(checkedList)

    }

    private fun existInDatabase(url: String): Boolean {
        val result = db.photoDao().getPhoto(url)
        if (result.isEmpty()) {
            return true
        }
        return false
    }

//    private suspend fun getPhoto(): List<String> {
//        val photoList = getPhotos().photos.photo
//        val outputList = arrayListOf<String>()
//
//        photoList.forEach {
//            val urlString = "https://live.staticflickr.com/${it.serverId}/${it.id}_${it.secret}.jpg"
//            outputList.add(urlString)
//        }
//        return outputList
//    }

    fun saveRecord(record: Photo) {
        db.photoDao().add(record)
    }

    suspend fun getNextImageUrl(): String {
        return getPhotoBuffer().first()
    }

    companion object {
        private var _instance: PhotoRaterRepository? = null
        fun initialize(context: Context) {
            if (_instance == null) {
                _instance = PhotoRaterRepository(context)
            }
        }

        fun getInstance(): PhotoRaterRepository {
            if (_instance == null) {
                throw Exception("Repository hasn't been initialized")
            } else {
                return _instance!!
            }
        }
    }
}