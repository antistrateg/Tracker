package ru.developmentmobile.tracker.map.presentation.ui

import androidx.lifecycle.*
import ru.developmentmobile.tracker.map.presentation.ui.viewmodels.MapDataFactory
import ru.developmentmobile.tracker.map.presentation.ui.viewmodels.MapDataHolder
import ru.developmentmobile.tracker.map.presentation.ui.viewmodels.MapUiEvents
import ru.developmentmobile.tracker.map.presentation.ui.viewmodels.MapUiModel

class MapViewModel(
) : ViewModel() {

    private val factory = MapDataFactory()

    private val cachedData = MapDataHolder()

    private val uiEventsObserver: Observer<MapUiEvents> = Observer { handleUiEvents(it) }

    private val uiDataMutable = MutableLiveData<MapUiModel>()

    val uiEvents = MutableLiveData<MapUiEvents>()

    val uiData: LiveData<MapUiModel> = uiDataMutable

    init {
        uiEvents.observeForever(uiEventsObserver)
    }

    private fun handleUiEvents(mapUiEvents: MapUiEvents) {

    }

    private fun postValue(mapUiModel: MapUiModel) =
        uiDataMutable.postValue(factory.createUiModel(cachedData, mapUiModel))
}
