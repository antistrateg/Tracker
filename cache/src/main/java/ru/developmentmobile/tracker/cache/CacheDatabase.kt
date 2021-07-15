package ru.developmentmobile.tracker.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.developmentmobile.tracker.cache.dao.MapLocationCachedDao
import ru.developmentmobile.tracker.cache.dao.MapTrackCachedDao
import ru.developmentmobile.tracker.cache.model.MapLocationCached
import ru.developmentmobile.tracker.cache.model.MapTrackCached

@Database(
    entities = [
        MapLocationCached::class,
        MapTrackCached::class

    ], version = 6, exportSchema = false
)

@TypeConverters(MapPointConverter::class)

abstract class CacheDatabase : RoomDatabase() {

    abstract fun mapTrackCachedDao(): MapTrackCachedDao
    abstract fun mapLocationCachedDao(): MapLocationCachedDao

}
