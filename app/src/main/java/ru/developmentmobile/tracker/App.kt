package ru.developmentmobile.tracker

import android.app.Application
//import org.koin.android.ext.koin.androidContext
//import org.koin.core.context.startKoin
//import ru.developmentmobile.tracker.di.injectApp
//import ru.developmentmobile.tracker.map.di.injectFeatureMap
//import ru.developmentmobile.tracker.splash.di.injectFeatureSplash

class App : Application() {

    override fun onCreate() {
        super.onCreate()

//        startKoin {
//            androidContext(this@App)
//        }
//        injectApp()
//        injectFeatureSplash()
//        injectFeatureMap()
    }
}
