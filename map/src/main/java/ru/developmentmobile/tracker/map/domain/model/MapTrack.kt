package ru.developmentmobile.tracker.map.domain.model

import com.google.android.gms.maps.model.LatLng

data class MapTrack (
    val id: Int,
    val name: String,
    val locations: List<MapPoint>
)



