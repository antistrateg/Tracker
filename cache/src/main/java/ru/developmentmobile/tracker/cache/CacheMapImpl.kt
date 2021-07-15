package ru.developmentmobile.tracker.cache

import android.util.Log
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.developmentmobile.tracker.cache.model.*
import ru.developmentmobile.tracker.map.datasourse.cache.MapCache
import ru.developmentmobile.tracker.map.domain.model.MapLocation
import ru.developmentmobile.tracker.map.domain.model.MapTrack

class CacheMapImpl : MapCache, KoinComponent {

    private val db: CacheDatabase by inject()

    override suspend fun getLocations(): List<MapLocation> {

        return db.mapLocationCachedDao().selectAll().mapFromCache()

    }

    override suspend fun deleteLocation(locationId: Int): Boolean {
        db.mapLocationCachedDao().deleteById(locationId)
        return true
    }

    override fun addSingleLocation(location: MapLocation): Boolean {
        db.mapLocationCachedDao().insert(location.mapToCache())
        return true
    }

    override suspend fun deleteLocations(): Boolean {
        db.mapLocationCachedDao().deleteAll()
        return true
    }

    override suspend fun getTracks(): List<MapTrack> {
        return emptyList()//listOf(
//            MapTrackCached(1,"Track_1", MapPointListSerializable(emptyList())),
//            MapTrackCached(1,"Track_2", MapPointListSerializable(emptyList()))
//            MapTrackCached(1,"Track_3", emptyList()),
//            MapTrackCached(1,"Track_4", emptyList()),
//            MapTrackCached(1,"Track_5", emptyList())
//        ).mapFromCache()
    }

    override suspend fun updateTrack(trackId: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteTrack(trackId: Int): Boolean {
        //db.mapTrackCachedDao().deleteById(trackId)
        return true
    }

    override suspend fun deleteTracks(): Boolean {
//        db.mapTrackCachedDao().deleteAll()
        return true
    }
}