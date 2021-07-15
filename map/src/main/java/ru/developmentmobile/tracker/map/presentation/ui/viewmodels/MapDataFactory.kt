package ru.developmentmobile.tracker.map.presentation.ui.viewmodels

class MapDataFactory {

    fun createUiModel(data: MapDataHolder, mapUiModel: MapUiModel) =

        when(mapUiModel) {
            is MapUiModel.CreateSection -> {
                MapUiModel.CreateSection(
                    section = data.section
                )
            }
            is MapUiModel.LoadSectionData -> {
                MapUiModel.LoadSectionData(
                    section = data.section,
                    tracks = data.tracks,
                    locations = data.locations,
                    needToLoadMarkers = data.needToLoadMarkers,
                    beacons = data.beacons,
                    beacon = data.beacon
                )
            }
            is MapUiModel.ClickTrackItem -> {
                MapUiModel.ClickTrackItem(
                    track = data.track
                )
            }
            is MapUiModel.ClickLocationItem -> {
                MapUiModel.ClickLocationItem(
                    location = data.location
                )
            }
            is MapUiModel.ClickBeaconItem -> {
                MapUiModel.ClickBeaconItem(
                    beacon = data.beacon
                )
            }
            is MapUiModel.UpdateLocation -> {
                MapUiModel.UpdateLocation(
                    location = data.location
                )
            }
            is MapUiModel.UpdateBeacon -> {
                MapUiModel.UpdateBeacon(
                    observeBeaconSwitch = data.observeBeaconSwitch,
                    beacon = data.beacon
                )
            }

            is MapUiModel.ShowProgressSectionData -> {
                mapUiModel
            }
            is MapUiModel.ShowProgressAddSingleLocation -> {
                mapUiModel
            }

        }

}