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
                    needToLoadMarkers = data.needToLoadMarkers
                )
            }
        }

}