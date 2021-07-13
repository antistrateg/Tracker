package ru.developmentmobile.tracker.map.domain.model

import com.google.android.gms.maps.model.LatLng

data class MapLocation (
    val id: Int = 0,
    val location: MapPoint,
    val title: String? = "",
    val address: String
)



