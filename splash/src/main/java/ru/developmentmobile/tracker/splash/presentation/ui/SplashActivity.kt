package ru.developmentmobile.tracker.splash.presentation.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import ru.developmentmobile.tracker.splash.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    companion object {
        @IdRes val SPLASH_NAVIGATION_ID = R.id.splash_nav_graph
    }
}
