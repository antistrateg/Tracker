package ru.developmentmobile.tracker.map.presentation.ui.viewmodels

import ru.developmentmobile.tracker.map.domain.model.MapBeacon
import ru.developmentmobile.tracker.map.domain.model.MapLocation
import ru.developmentmobile.tracker.map.domain.model.MapTrack
import ru.developmentmobile.tracker.map.presentation.ui.MapFragment

sealed class MapUiEvents {
    data class CreateSection(val section: MapFragment.Section) : MapUiEvents()
    data class LoadSectionData(val section: MapFragment.Section) : MapUiEvents()
    data class AddSingleLocation(val location: MapLocation) : MapUiEvents()
    data class ClickTrackItem(val track: MapTrack) : MapUiEvents()
    data class DeleteTrack(val track: MapTrack) : MapUiEvents()
    data class ClickLocationItem(val locationId: Int) : MapUiEvents()
    data class DeleteLocation(val locationId: Int) : MapUiEvents()
    data class ClickBeaconItem(val beacon: MapBeacon) : MapUiEvents()

    object DeleteTracks : MapUiEvents()
    object DeleteLocations : MapUiEvents()
    object ObserveBeacon : MapUiEvents()
}