package ru.developmentmobile.tracker.router.map

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.developmentmobile.tracker.map.R
import ru.developmentmobile.tracker.map.presentation.router.MapRouter
import ru.developmentmobile.tracker.router.splash.findNavControllerAndNavigate
import ru.developmentmobile.tracker.splash.presentation.ui.SplashActivity

class MapRouterImpl : MapRouter {
    override fun navigateNextToMap(fragment: Fragment) {

        //fragment.findNavControllerAndNavigate(SplashActivity.SPLASH_NAVIGATION_ID)
        fragment.findNavController().navigate(R.id.mapFragment)
    }
}