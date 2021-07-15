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
            is MapUiEvents.AddSingleLocation -> {
                cachedData.location = mapUiEvents.location
                addSingleLocation()
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
            is MapUiEvents.DeleteLocation -> {
                cachedData.location = cachedData.locations.find { it.id == mapUiEvents.locationId }
                deleteLocation()
            }
            is MapUiEvents.DeleteLocations -> {
                deleteLocations()
            }
            is MapUiEvents.DeleteTrack -> {
                cachedData.track = mapUiEvents.track
                deleteTrack()
            }
            is MapUiEvents.DeleteTracks -> {
                deleteTracks()
            }
            is MapUiEvents.ObserveBeacon -> {
                observeBeacon()
            }
        }
    }

    private fun loadTracks() {
        viewModelScope.launch(Dispatchers.IO) {
            postValue(MapUiModel.ShowProgressSectionData(cachedData.section, true))
            delay(50)
            //TODO cachedData.tracks = mapTrackInteractor.getTracks()
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
            //TODO cachedData.locations = listOf(MapLocation(1,MapPoint(0.0,0.0),"",""))// mapLocationInteractor.getLocations()
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
            //TODO cachedData.beacons = mapBeaconInteractor.getBeacons()
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

    private fun addSingleLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            //load data
            postValue(MapUiModel.ShowProgressAddSingleLocation(true))
            delay(20)
            //TODO mapLocationInteractor.addSingleLocation(cachedData.location!!)
            delay(1000)
            if (cachedData.section == MapFragment.Section.LOCATIONS) {
                postValue(MapUiModel.ShowProgressAddSingleLocation(false))
                delay(20)
                postValue(MapUiModel.UpdateLocation())
                delay(20)
                loadLocations(false)
            }
        }
    }

    private fun deleteTrack() {
        viewModelScope.launch(Dispatchers.IO) {
            //TODO mapTrackInteractor.deleteTrack(cachedData.track!!.id)
            if (cachedData.section == MapFragment.Section.TRACKS) {
                loadTracks()
            }
        }
    }

    private fun deleteTracks() {
        viewModelScope.launch(Dispatchers.IO) {
            //TODO mapTrackInteractor.deleteTracks()
            if (cachedData.section == MapFragment.Section.TRACKS) {
                loadTracks()
            }
        }
    }

    private fun deleteLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            //TODO mapLocationInteractor.deleteLocation(cachedData.location!!.id)
            if (cachedData.section == MapFragment.Section.LOCATIONS) {
                loadLocations(true)
            }
        }
    }

    private fun deleteLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            //TODO mapLocationInteractor.deleteLocations()
            if (cachedData.section == MapFragment.Section.LOCATIONS) {
                loadLocations(true)
            }
        }
    }

    private fun observeBeacon() {
        viewModelScope.launch(Dispatchers.IO) {

            cachedData.beacon?.let {
                cachedData.observeBeaconSwitch = cachedData.observeBeaconSwitch.not()
                while (cachedData.observeBeaconSwitch) {
                    //spy the beacon
                    //TODO val request = mapBeaconInteractor.getBeacon(it.id)

                    if (cachedData.section == MapFragment.Section.BEACON) postValue(MapUiModel.UpdateBeacon())
                    else cachedData.observeBeaconSwitch = false
                    delay(3000)
                }
                if (cachedData.section == MapFragment.Section.BEACON) postValue(MapUiModel.BeaconObserveStopped)
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
