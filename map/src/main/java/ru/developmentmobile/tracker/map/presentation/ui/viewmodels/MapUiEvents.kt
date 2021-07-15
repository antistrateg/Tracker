package ru.developmentmobile.tracker.map.presentation.ui.viewmodels

import ru.developmentmobile.tracker.map.presentation.ui.MapFragment

sealed class MapUiEvents {
    data class CreateSection(val section: MapFragment.Section) : MapUiEvents()
    data class LoadSectionData(val section: MapFragment.Section) : MapUiEvents()
}