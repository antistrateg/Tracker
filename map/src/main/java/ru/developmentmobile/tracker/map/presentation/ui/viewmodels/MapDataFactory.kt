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

            is MapUiModel.ShowProgressSectionData -> {
                mapUiModel
            }

        }

}