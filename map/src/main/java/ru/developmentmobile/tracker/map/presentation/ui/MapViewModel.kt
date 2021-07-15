package ru.developmentmobile.tracker.map.presentation.ui

import android.location.Location
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.developmentmobile.tracker.map.domain.model.MapLocation
import ru.developmentmobile.tracker.map.domain.model.MapPoint
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
            is MapUiEvents.ClickLocationItem -> {
                cachedData.location = cachedData.locations.find { it.id == mapUiEvents.locationId }
                clickLocationItem()
            }
            is MapUiEvents.ClickBeaconItem -> {
                cachedData.beacon = mapUiEvents.beacon
                clickBeaconItem()
            }
            is MapUiEvents.ClickTrackItem -> {
                cachedData.track = mapUiEvents.track
                clickTrackItem()
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
            //cachedData.locations = listOf(MapLocation(1,MapPoint(0.0,0.0),"",""))// mapLocationInteractor.getLocations()
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

    private fun clickTrackItem() {
        viewModelScope.launch(Dispatchers.IO) {
            if (cachedData.section == MapFragment.Section.TRACKS) {
                postValue(MapUiModel.ClickTrackItem())
            }
        }
    }
    private fun clickLocationItem() {
        viewModelScope.launch(Dispatchers.IO) {
            if (cachedData.section == MapFragment.Section.LOCATIONS) {
                postValue(MapUiModel.ClickLocationItem())
            }
        }
    }
    private fun clickBeaconItem() {
        viewModelScope.launch(Dispatchers.IO) {
            if (cachedData.section == MapFragment.Section.BEACON) {
                postValue(MapUiModel.ClickBeaconItem())
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
