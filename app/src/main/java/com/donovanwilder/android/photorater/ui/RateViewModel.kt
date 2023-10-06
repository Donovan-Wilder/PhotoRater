package com.donovanwilder.android.photorater.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donovanwilder.android.photorater.PhotoRaterRepository
import com.donovanwilder.android.photorater.database.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

private const val TAG = "RateViewModel"

class RateViewModel : ViewModel() {
    private val repository = PhotoRaterRepository.getInstance()

    private val _ratingsValue = MutableStateFlow(.5f)
    val ratingsValue: StateFlow<Float> = _ratingsValue.asStateFlow()
    var ratingsText: String = ""
        private set
        get() = (_ratingsValue.value * 10).toInt().toString()
    private val _imageUrl = MutableStateFlow("")
    val imageUrl: StateFlow<String> = _imageUrl.asStateFlow()


    fun setRating(rating: Float) {
        _ratingsValue.value = rating

    }

    fun getNextEntry() {
        if (_imageUrl.value.isNotEmpty()) {
            saveRating()
        }
        _ratingsValue.value = 0.5f
        getNextImage()
    }

    private fun getNextImage() {
        viewModelScope.launch {
            repository.getNextImageUrl()
        }
    }

    private fun saveRating() {
        val record = Photo(0, imageUrl.value, Date(), ratingsText.toInt())
        repository.saveRecord(record)
    }

//    fun getImage() {
//        viewModelScope.launch {
//            val urlList = PhotoRaterRepository.getInstance().getPhotosUrl()
//            _imageUrl.value = urlList[0]
////            Log.d(TAG, urlList.toString())
//        }
//    }
}