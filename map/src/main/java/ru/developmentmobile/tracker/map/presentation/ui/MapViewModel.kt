package ru.developmentmobile.tracker.map.presentation.ui

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        when (mapUiEvents) {
            is MapUiEvents.CreateSection -> {
                cachedData.section = mapUiEvents.section
                postValue(MapUiModel.CreateSection())
            }
            is MapUiEvents.LoadSectionData -> {
                cachedData.section = mapUiEvents.section
                when (cachedData.section) {
                    MapFragment.Section.TRACKS -> loadTracks()
                    MapFragment.Section.LOCATIONS -> loadLocations(true)
                    MapFragment.Section.BEACON -> loadBeacons()
                }
            }
        }
    }

    private fun loadTracks() {
        viewModelScope.launch(Dispatchers.IO) {
            postValue(MapUiModel.ShowProgressSectionData(cachedData.section, true))
            delay(50)
            //cachedData.tracks = mapTrackInteractor.getTracks()
            delay(1000)
            if (cachedData.section == MapFragment.Section.TRACKS) {
                postValue(MapUiModel.LoadSectionData())
                delay(50)
                postValue(MapUiModel.ShowProgressSectionData(cachedData.section, false))
            }
        }
    }
    private fun loadLocations(needToLoadMarkers: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            cachedData.needToLoadMarkers = needToLoadMarkers
            postValue(MapUiModel.ShowProgressSectionData(cachedData.section, true))
            delay(50)
            //cachedData.locations = mapLocationInteractor.getLocations()
            delay(1000)
            if (cachedData.section == MapFragment.Section.LOCATIONS) {
                postValue(MapUiModel.LoadSectionData())
                delay(50)
                postValue(MapUiModel.ShowProgressSectionData(cachedData.section, false))
            }
        }
    }
    private fun loadBeacons() {
        viewModelScope.launch(Dispatchers.IO) {
            postValue(MapUiModel.ShowProgressSectionData(cachedData.section, true))
            delay(50)
            //cachedData.beacons = mapBeaconInteractor.getBeacons()
            delay(1000)
            if (cachedData.section == MapFragment.Section.BEACON) {
                postValue(MapUiModel.LoadSectionData())
                delay(50)
                postValue(MapUiModel.ShowProgressSectionData(cachedData.section, false))
            }
        }
    }


    private fun postValue(mapUiModel: MapUiModel) =
        uiDataMutable.postValue(factory.createUiModel(cachedData, mapUiModel))

    override fun onCleared() {
        Log.d("TAG", "Cleared")
        super.onCleared()
    }
}
