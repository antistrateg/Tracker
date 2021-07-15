package ru.developmentmobile.tracker.map.data.remote

import ru.developmentmobile.tracker.map.datasourse.model.MapBeaconEntityWrapper
import ru.developmentmobile.tracker.map.datasourse.model.MapBeaconsEntityWrapper
import ru.developmentmobile.tracker.map.network.Response

interface MapRemoteDataSource {

    suspend fun getBeacon(beaconId: Int): Response<MapBeaconEntityWrapper>
    suspend fun getBeacons(): Response<MapBeaconsEntityWrapper>

}