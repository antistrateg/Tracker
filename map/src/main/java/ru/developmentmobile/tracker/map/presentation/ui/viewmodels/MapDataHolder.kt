package ru.developmentmobile.tracker.map.presentation.ui.viewmodels

import ru.developmentmobile.tracker.map.domain.model.MapBeacon
import ru.developmentmobile.tracker.map.domain.model.MapLocation
import ru.developmentmobile.tracker.map.domain.model.MapTrack
import ru.developmentmobile.tracker.map.presentation.ui.MapFragment

data class MapDataHolder(
    var section: MapFragment.Section = MapFragment.Section.TRACKS,
    var track: MapTrack? = null,
    var tracks: List<MapTrack> = emptyList(),
    var location: MapLocation? = null,
    var locations: List<MapLocation> = emptyList(),
    var needToLoadMarkers: Boolean = false,
    var observeBeaconSwitch: Boolean = false,
    var beacon: MapBeacon? = null,
    var beacons: List<MapBeacon> = emptyList(),
    var isLoadingTracks: Boolean = false
)