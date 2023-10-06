package com.donovanwilder.android.photorater.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlickrPhoto(
    @SerialName("server") val serverId: String,
    @SerialName("id") val id: String,
    @SerialName("secret") val secret: String
)
@Serializable
data class FlickerWrapper2(
    val photo: List<FlickrPhoto>
)
@Serializable
data class FlickrWrapper1(
    val photos: FlickerWrapper2
)
