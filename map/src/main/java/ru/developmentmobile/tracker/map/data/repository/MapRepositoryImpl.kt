package ru.developmentmobile.tracker.map.data.repository

import ru.developmentmobile.tracker.map.data.cache.MapCacheDataSource
import ru.developmentmobile.tracker.map.data.remote.MapRemoteDataSource
import ru.developmentmobile.tracker.map.datasourse.model.mapToDomain
import ru.developmentmobile.tracker.map.domain.model.MapBeacon
import ru.developmentmobile.tracker.map.domain.model.MapLocation
import ru.developmentmobile.tracker.map.domain.model.MapTrack
import ru.developmentmobile.tracker.map.domain.repository.MapRepository
import ru.developmentmobile.tracker.map.network.Failure
import ru.developmentmobile.tracker.map.network.Response
import ru.developmentmobile.tracker.map.network.Success

class MapRepositoryImpl constructor(
    private val cacheDataSource: MapCacheDataSource,
    private val remoteDataSource: MapRemoteDataSource
) : MapRepository {

    override suspend fun getTracks() =
        try {
            cacheDataSource.getTracks()
        } catch (e: RuntimeException) {
            e.printStackTrace()
            emptyList<MapTrack>()
        }


    override suspend fun updateTrack(trackId: Int) = cacheDataSource.updateTrack(trackId)

    override suspend fun deleteTrack(trackId: Int) =
        try {
            cacheDataSource.deleteTrack(trackId)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            false
        }

    override suspend fun deleteTracks() =
        try {
            cacheDataSource.deleteTracks()
        } catch (e: RuntimeException) {
            e.printStackTrace()
            false
        }

    override suspend fun addSingleLocation(location: MapLocation): Boolean {
        try {
            cacheDataSource.addSingleLocation(location)
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
        return true
    }


    override suspend fun getLocations() =
        try {
            cacheDataSource.getLocations()
        } catch (e: RuntimeException) {
            e.printStackTrace()
            emptyList<MapLocation>()
        }



    override suspend fun deleteLocation(locationId: Int) =
        try {
            cacheDataSource.deleteLocation(locationId)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            false
        }


    override suspend fun deleteLocations() =
        try {
            cacheDataSource.deleteLocations()
        } catch (e: RuntimeException) {
            e.printStackTrace()
            false
        }

    override suspend fun getBeacon(beaconId: Int) =

        when (val request = remoteDataSource.getBeacon(beaconId)) {
            is Success -> {
                Response.success(request.data.beacon.mapToDomain())
            }
            is Failure -> {
                Response.failure<MapBeacon>(request.error)
            }
        }


    override suspend fun getBeacons() =

        when (val request = remoteDataSource.getBeacons()) {
            is Success -> {
                Response.success(request.data.beacons.mapToDomain())
            }
            is Failure -> {
                Response.failure<List<MapBeacon>>(request.error)
            }
        }

}
