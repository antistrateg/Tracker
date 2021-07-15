package ru.developmentmobile.tracker.map.presentation.ui.viewmodels

import ru.developmentmobile.tracker.map.domain.model.MapBeacon
import ru.developmentmobile.tracker.map.domain.model.MapLocation
import ru.developmentmobile.tracker.map.domain.model.MapTrack
import ru.developmentmobile.tracker.map.presentation.ui.MapFragment

sealed class MapUiModel {

    data class CreateSection(
        var section: MapFragment.Section = MapFragment.Section.LOCATIONS
    ) : MapUiModel()

    data class LoadSectionData(
        var section: MapFragment.Section = MapFragment.Section.LOCATIONS,
        var tracks: List<MapTrack> = emptyList(),
        var locations: List<MapLocation> = emptyList(),
        var needToLoadMarkers: Boolean = false,
        var beacons: List<MapBeacon> = emptyList(),
        var beacon: MapBeacon? = null
    ) : MapUiModel()

    data class ClickTrackItem(
        var track: MapTrack? = null
    ) : MapUiModel()

    data class ClickLocationItem(
        var location: MapLocation? = null
    ) : MapUiModel()

    data class ClickBeaconItem(
        var beacon: MapBeacon? = null
    ) : MapUiModel()

    data class UpdateLocation(
        var location: MapLocation? = null
    ) : MapUiModel()

    data class UpdateBeacon(
        var observeBeaconSwitch: Boolean = false,
        var beacon: MapBeacon? = null
    ) : MapUiModel()

    data class ShowProgressSectionData(
        var section: MapFragment.Section = MapFragment.Section.LOCATIONS,
        var isLoading: Boolean = false
    ) : MapUiModel()

    data class ShowProgressAddSingleLocation(
        var isLoading: Boolean = false
    ) : MapUiModel()

    object BeaconObserveStopped: MapUiModel()

    object Error : MapUiModel()

}