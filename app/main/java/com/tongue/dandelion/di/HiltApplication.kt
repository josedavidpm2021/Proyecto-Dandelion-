package com.tongue.dandelion.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication: Application() {

    public var _instance: HiltApplication? = null

    init {
        _instance = this
    }
    public fun getInstance(): HiltApplication {
        return _instance!!
    }

}