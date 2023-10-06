package src

import android.app.Application
import com.donovanwilder.android.photorater.PhotoRaterRepository

class PhotoRaterApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        PhotoRaterRepository.initialize(this)
    }
}