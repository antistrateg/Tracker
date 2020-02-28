package ru.developmentmobile.tracker.map.datasourse.remote

import com.google.android.gms.maps.model.LatLng
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import ru.developmentmobile.tracker.map.data.remote.MapRemoteDataSource
import ru.developmentmobile.tracker.map.datasourse.model.MapBeaconEntityWrapper
import ru.developmentmobile.tracker.map.domain.model.MapBeacon
import ru.developmentmobile.tracker.map.network.NetworkClient
import ru.developmentmobile.tracker.map.network.Response

class MapRemoteDataSourceImpl(
    private val network: NetworkClient,
    private val api: MapApi
) : MapRemoteDataSource, KoinComponent {


    override suspend fun getBeacon(beaconId: Int): Response<MapBeaconEntityWrapper> {

//        network.request { api.getBeacon(beaconId)}

        return network.request { api.getBeacon(beaconId)}
    }

    override suspend fun getBeacons() = network.request { api.getBeacons()}
}

