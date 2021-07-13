package ru.developmentmobile.tracker.map.domain.model

import com.google.android.gms.maps.model.LatLng

data class MapBeacon (
    val id: Int,
    val name: String,
    val location: MapPoint,
    val time: Long
)



