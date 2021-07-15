package ru.developmentmobile.tracker.map.domain.repository

import ru.developmentmobile.tracker.map.domain.model.MapBeacon
import ru.developmentmobile.tracker.map.domain.model.MapLocation
import ru.developmentmobile.tracker.map.domain.model.MapTrack
import ru.developmentmobile.tracker.map.network.Response

interface MapRepository {

    //Track
    suspend fun getTracks(): List<MapTrack>
    suspend fun updateTrack(trackId: Int): Boolean
    suspend fun deleteTrack(trackId: Int): Boolean
    suspend fun deleteTracks(): Boolean

    //Location
    suspend fun addSingleLocation(location: MapLocation): Boolean
    suspend fun getLocations(): List<MapLocation>
    suspend fun deleteLocation(locationId: Int): Boolean
    suspend fun deleteLocations(): Boolean

    //Beacon
    suspend fun getBeacon(beaconId: Int): Response<MapBeacon>
    suspend fun getBeacons(): Response<List<MapBeacon>>
}