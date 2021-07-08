package ru.developmentmobile.tracker.router.map

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.developmentmobile.tracker.map.R
import ru.developmentmobile.tracker.map.presentation.router.MapRouter

class MapRouterImpl : MapRouter {
    override fun navigateNextToMap(fragment: Fragment) {
        fragment.findNavController().navigate(R.id.mapFragment)
    }
}