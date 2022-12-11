package net.pelozo.poketeams_challenge

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.internal.Contexts.getApplication

@HiltAndroidApp
class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.setClientToken(getString(R.string.facebook_application_id))
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(getApplication(applicationContext))
    }
}