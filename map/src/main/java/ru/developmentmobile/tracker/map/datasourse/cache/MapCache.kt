package ru.developmentmobile.tracker.map.datasourse.cache

import ru.developmentmobile.tracker.map.domain.model.MapLocation
import ru.developmentmobile.tracker.map.domain.model.MapTrack

interface MapCache {

    suspend fun getLocations(): List<MapLocation>

    suspend fun deleteLocation(locationId: Int): Boolean

    fun addSingleLocation(location: MapLocation): Boolean

    suspend fun deleteLocations(): Boolean

    suspend fun getTracks(): List<MapTrack>

    suspend fun updateTrack(trackId: Int): Boolean

    suspend fun deleteTrack(trackId: Int): Boolean

    suspend fun deleteTracks(): Boolean

}
