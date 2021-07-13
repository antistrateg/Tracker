package ru.developmentmobile.tracker.map.presentation.ui.viewmodels

import ru.developmentmobile.tracker.map.domain.model.MapBeacon
import ru.developmentmobile.tracker.map.domain.model.MapLocation
import ru.developmentmobile.tracker.map.domain.model.MapTrack
import ru.developmentmobile.tracker.map.presentation.ui.MapFragment

sealed class MapUiModel {

    data class CreateSection(
        var section: MapFragment.Section = MapFragment.Section.TRACKS
    ) : MapUiModel()

    data class LoadSectionData(
        var section: MapFragment.Section = MapFragment.Section.TRACKS,
        var tracks: List<MapTrack> = emptyList(),
        var locations: List<MapLocation> = emptyList(),
        var needToLoadMarkers: Boolean = false,
        var beacons: List<MapBeacon> = emptyList(),
        var beacon: MapBeacon? = null
    ) : MapUiModel()

}