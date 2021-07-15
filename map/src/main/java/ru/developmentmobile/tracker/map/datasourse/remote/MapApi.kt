package ru.developmentmobile.tracker.map.datasourse.remote

import retrofit2.http.*
import ru.developmentmobile.tracker.map.datasourse.model.MapBeaconEntityWrapper
import ru.developmentmobile.tracker.map.datasourse.model.MapBeaconsEntityWrapper
import ru.developmentmobile.tracker.network.ResponseModel

interface MapApi {

    @GET(REQUEST_BEACON)
    suspend fun getBeacon(
        @Query(BEACON_ID) beaconId: Int? = null,
        @Query("generated") generated: Boolean = true
    ): ResponseModel<MapBeaconEntityWrapper>

    @GET(REQUEST_BEACONS)
    suspend fun getBeacons(
        @Query("generated") generated: Boolean = true
    ): ResponseModel<MapBeaconsEntityWrapper>

    companion object {
        //endpoints
        private const val REQUEST_BEACON= "api/beacon"
        private const val REQUEST_BEACONS = "api/beacons"

        //parameters
        private const val BEACON_ID = "id"
    }
}