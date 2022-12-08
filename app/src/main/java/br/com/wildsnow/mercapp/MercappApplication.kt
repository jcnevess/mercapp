package br.com.wildsnow.mercapp

import android.app.Application
import timber.log.Timber


class MercappApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("Timber started.")
        }
    }
}