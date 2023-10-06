package com.donovanwilder.android.photorater.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

private const val FLICKER_API_KEY = "20c339bb6ff47486c377c861d395aa18"
private const val TAG = "FlickerSource"

object FlickerApi {
    private var currentPage = 0
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json{ignoreUnknownKeys =true}.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://www.flickr.com/services/")
        .build()
    private val retrofitService: FlickerService by lazy {
        retrofit.create(FlickerService::class.java)
    }

    suspend fun getPhotosUrl(): List<String> {
        val result = retrofitService.search(page = currentPage++).photos.photo
        val urlStringList = arrayListOf<String>()
        result.forEach{
            urlStringList.add("https://live.staticflickr.com/${it.serverId}/${it.id}_${it.secret}.jpg")
        }

        return urlStringList
    }
}

private interface FlickerService {
    @GET("rest")
    suspend fun getPhotos(
        @Query("method") method: String = "flickr.panda.getPhotos",
        @Query("api_key") apiKey: String = FLICKER_API_KEY,
        @Query("panda_name") pandaName: String = "hsing hsing",
        @Query("lastupdate") extras: String = Date().time.toString()
    ): String

    @GET("rest")
    suspend fun search(
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey: String = FLICKER_API_KEY,
        @Query("text") text: String = "Cows",
        @Query("format") format: String = "json",
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 1,
        @Query("nojsoncallback") callback: Int = 1
    ): FlickrWrapper1
}
