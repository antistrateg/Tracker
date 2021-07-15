package ru.developmentmobile.tracker.map.datasourse.cache

import ru.developmentmobile.tracker.map.data.cache.MapCacheDataSource
import ru.developmentmobile.tracker.map.domain.model.MapLocation

class MapCacheDataSourceImpl(
    private val mapCache: MapCache
) : MapCacheDataSource {

    override suspend fun getTracks() = mapCache.getTracks()

    override suspend fun updateTrack(trackId: Int) = mapCache.updateTrack(trackId)

    override fun addSingleLocation(location: MapLocation) = mapCache.addSingleLocation(location)

    override suspend fun getLocations() = mapCache.getLocations()
    override suspend fun deleteLocation(locationId: Int) = mapCache.deleteLocation(locationId)

    override suspend fun deleteLocations() = mapCache.deleteLocations()

    override suspend fun deleteTrack(trackId: Int) = mapCache.deleteTrack(trackId)

    override suspend fun deleteTracks() = mapCache.deleteTracks()
}