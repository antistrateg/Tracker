package ru.developmentmobile.tracker.router.splash

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.activity
import androidx.navigation.fragment.findNavController
//import kotlinx.android.synthetic.main.activity_main.*
import ru.developmentmobile.tracker.R
import ru.developmentmobile.tracker.map.presentation.ui.MapActivity
import ru.developmentmobile.tracker.splash.presentation.router.SplashRouter

class SplashRouterImpl() : SplashRouter {

    override fun navigateNextToSplash(fragment: Fragment) {

//        fragment.findNavController().popBackStack()
        fragment.findNavController().navigate(MapActivity.MAP_NAVIGATION_ID)
//        fragment.findNavControllerAndNavigate(MapActivity.MAP_NAVIGATION_ID)
//        fragment.findNavController().popBackStack()
        fragment.requireActivity().finish()

    }
}

fun Fragment.findNavControllerAndNavigate(@IdRes id: Int) {
    val navController = this.findNavController()
    navController.graph = navController.navInflater.inflate(R.navigation.app_nav_graph)
    navController.navigate(MapActivity.MAP_NAVIGATION_ID)
}