package ru.developmentmobile.tracker.map.presentation.ui.viewmodels

import ru.developmentmobile.tracker.map.presentation.ui.MapFragment

sealed class MapUiModel {

    data class CreateSection(
        var section: MapFragment.Section = MapFragment.Section.TRACKS
    ) : MapUiModel()

    data class LoadSectionData(
        var section: MapFragment.Section = MapFragment.Section.TRACKS,
        var needToLoadMarkers: Boolean = false
    ) : MapUiModel()

}